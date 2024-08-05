 document.addEventListener("DOMContentLoaded", function() {
            fetch('/admin/category-stats')
                .then(response => response.json())
                .then(data => {
                    const ctx = document.getElementById('categoryChart').getContext('2d');
                    const colors = [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(255, 159, 64, 0.2)',
                        'rgba(0, 255, 127, 0.2)',
                        'rgba(255, 99, 71, 0.2)'
                    ];
                    const borderColors = [
                        'rgba(255, 99, 132, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 159, 64, 1)',
                        'rgba(0, 255, 127, 1)',
                        'rgba(255, 99, 71, 1)'
                    ];
                    const backgroundColors = data.map((_, index) => colors[index % colors.length]);
                    const borderColorsMapped = data.map((_, index) => borderColors[index % borderColors.length]);

                    const chartData = {
                        labels: data.map(item => item.name),
                        datasets: [{
                            label: '',
                            data: data.map(item => item.productCount),
                            backgroundColor: backgroundColors,
                            borderColor: borderColorsMapped,
                            borderWidth: 1
                        }]
                    };

                    const categoryChart = new Chart(ctx, {
                        type: 'bar',
                        data: chartData,
                        options: {
                            scales: {
                                y: {
                                    beginAtZero: true
                                }
                            },
                            plugins: {
                                legend: {
                                    labels: {
                                        boxWidth: 0 // Ẩn ô vuông màu
                                    }
                                },
                                datalabels: {
                                    anchor: 'end',
                                    align: 'end',
                                    color: 'black',
                                    font: {
                                        weight: 'bold'
                                    },
                                    formatter: function(value, context) {
                                        return value;
                                    }
                                }
                            }
                        },
                        plugins: [ChartDataLabels]
                    });
                })
                .catch(error => console.error('Error fetching category stats:', error));
        });

        function showSection(sectionId) {
            const sections = document.querySelectorAll('article > div');
            sections.forEach(section => {
                if (section.id === sectionId) {
                    section.classList.remove('hidden');
                } else {
                    section.classList.add('hidden');
                }
            });

            const buttons = document.querySelectorAll('.nav-button');
            buttons.forEach(button => {
                if (button.getAttribute('onclick') === `showSection('${sectionId}')`) {
                    button.classList.add('active');
                } else {
                    button.classList.remove('active');
                }
            });
        }