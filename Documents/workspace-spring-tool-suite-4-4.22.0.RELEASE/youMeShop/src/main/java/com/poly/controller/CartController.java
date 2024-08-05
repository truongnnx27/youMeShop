package com.poly.controller;

import com.poly.entity.Order;
import com.poly.entity.OrderDetail;
import com.poly.entity.Product;
import com.poly.entity.User;
import com.poly.repository.OderRepository;
import com.poly.repository.UsersRepository;
import com.poly.repository.oderDetailRepository;
import com.poly.service.OrderDetailService;
import com.poly.service.ProductService;
import com.poly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private oderDetailRepository orderDetailRepository;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OderRepository oderRepository;
    
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserService userService;

    private static final double SHIPPING_FEE = 30000;

    @GetMapping("/cart")
    public String cart(Model model) {
        List<OrderDetail> cart = orderDetailService.getCartForCurrentUser()
                                                   .stream()
                                                   .filter(item -> !item.getIsPaid()) //lọc sản phẩm chưa thanh toán
                                                   .collect(Collectors.toList());
        boolean isEmptyCart = cart.isEmpty();
        model.addAttribute("emptyCart", isEmptyCart);
        if (!isEmptyCart) {
            double subTotal = cart.stream().mapToDouble(OrderDetail::getAmount).sum();
            double total = subTotal + SHIPPING_FEE;
            LocalDate orderDate = LocalDate.now();
            
            // Tính tổng số lượng sản phẩm có trong giỏ hàng
            int totalQuantity = cart.stream().mapToInt(OrderDetail::getQuantity).sum();

            model.addAttribute("cart", cart);
            model.addAttribute("subTotal", subTotal);
            model.addAttribute("shippingFee", SHIPPING_FEE);
            model.addAttribute("total", total);
            model.addAttribute("orderDate", Date.valueOf(orderDate));
            model.addAttribute("totalQuantity", totalQuantity);
        } else {
            model.addAttribute("subTotal", 0);
            model.addAttribute("shippingFee", 0);
            model.addAttribute("total", 0);
            model.addAttribute("orderDate", null);
            model.addAttribute("totalQuantity", 0);
        }
        return "page/cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(
            @RequestParam("productId") String productId,
            @RequestParam("color") String color,
            @RequestParam("size") String size,
            @RequestParam("quantity") int quantity,
            Model model) {

        User user = userService.getCurrentUser();
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Product product = productService.findProductById(productId);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        List<OrderDetail> cartItems = orderDetailRepository.findByUserAndProductsAndColorAndSizeAndIsPaid(user, product, color, size, false);

        OrderDetail cartItem;
        if (cartItems.isEmpty()) {
            // Nếu sản phẩm chưa tồn tại trong giỏ hàng, tạo đơn hàng mới
            cartItem = new OrderDetail();
            cartItem.setIdOrderDetails(orderDetailService.generateOrderId());
            cartItem.setUser(user);
            cartItem.setProducts(product);
            cartItem.setColor(color);
            cartItem.setSize(size);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(product.getPrice());
            cartItem.setAmount(cartItem.getPrice() * cartItem.getQuantity());
            cartItem.setIsPaid(false); // Đảm bảo sản phẩm mới chưa được thanh toán
        } else {
            // Nếu sản phẩm đã tồn tại trong giỏ hàng và chưa thanh toán, cập nhật số lượng
            cartItem = cartItems.get(0);
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setAmount(cartItem.getPrice() * cartItem.getQuantity());
        }

        orderDetailRepository.save(cartItem);

        return "redirect:/cart";
    }





    @PostMapping("/cart/update")
    public String updateCartQuantity(
            @RequestParam("idOrderDetails") String idOrderDetails,
            @RequestParam("quantity") int quantity,
            Model model) {

        OrderDetail cartItem = orderDetailRepository.findById(idOrderDetails).orElse(null);
        if (cartItem != null) {
            cartItem.setQuantity(quantity);
            cartItem.setAmount(cartItem.getPrice() * quantity);
            orderDetailRepository.save(cartItem);
        }

        return "redirect:/cart";
    }

    @PostMapping("/cart/delete")
    public String deleteFormCart(
            @RequestParam("productId") String productId,
            @RequestParam("color") String color,
            @RequestParam("size") String size) {

        User user = userService.getCurrentUser();
        Product product = productService.findProductById(productId);
        List<OrderDetail> details = orderDetailRepository.findByUserAndProductsAndColorAndSize(user, product, color, size);

        if (details.size() == 1) {
            orderDetailRepository.delete(details.get(0));
        } else if (details.isEmpty()) {
            throw new RuntimeException("Product not found in cart");
        } else {
            // Nếu có nhiều hơn một sản phẩm, xóa tất cả
            for (OrderDetail detail : details) {
                orderDetailRepository.delete(detail);
            }
            // orderDetailRepository.delete(details.get(0));
        }

        return "redirect:/cart";
    }


    

    @PostMapping("/cart/checkout")
    public String checkout(RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = usersRepository.findByFullname(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<OrderDetail> cart = orderDetailService.getCartForCurrentUser()
                                                   .stream()
                                                   .filter(item -> !item.getIsPaid())
                                                   .collect(Collectors.toList());
        if (cart == null || cart.isEmpty()) {
            redirectAttributes.addFlashAttribute("emptyCart", true);
            return "redirect:/cart";
        }

        double totalPrice = cart.stream().mapToDouble(OrderDetail::getAmount).sum();
        double total = totalPrice + SHIPPING_FEE;
        Date orderDate = new Date(System.currentTimeMillis());
        boolean paymentStatus = true; // thanh toán thành công

        Order order = new Order();
        order.setIdOrder(orderDetailService.generateOrderId());
        order.setOrderDate(orderDate);
        order.setPaymentStatus(paymentStatus);
        order.setTotalPrice(total);
        order.setIsPaid(true);
        oderRepository.save(order);

        for (OrderDetail item : cart) {
            item.setOrder(order);
            item.setPaymentStatus(paymentStatus);
            item.setOrderDate(orderDate); // Cập nhật ngày thanh toán cho OrderDetail
            item.setIsPaid(true); // Đánh dấu sản phẩm đã thanh toán
            orderDetailRepository.save(item);
        }

        redirectAttributes.addFlashAttribute("paymentSuccess", true);
        return "redirect:/cart"; 
    }
}
