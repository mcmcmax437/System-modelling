import random
import math
import matplotlib.pyplot as plt
import numpy as np
from scipy.stats import chi2

parameters = [
    {'sigma': 1, 'alpha': 0},
    {'sigma': 3, 'alpha': 2},
    {'sigma': 5, 'alpha': 5}
]
bins = 250  
n = 10000
significance_level = 0.05

#Генератор
for idx, param_set in enumerate(parameters):
    sigma = param_set['sigma']
    alpha = param_set['alpha']

    # Генеруємо 12 рандомних чисел (від 0 до 1) xi і рахуємо mu_i (сума 12 чисел)
    mu_values = []
    for _ in range(n):
        xi_values = [random.random() for _ in range(12)]
        mu_i = sum(xi_values) - 6
        mu_values.append(mu_i)

    #Генеруємо фінальні числа на основі
    random_numbers = [sigma * mu + alpha for mu in mu_values]

    plt.figure(idx + 1)

    mean = np.mean(random_numbers)
    std_dev = np.std(random_numbers)

    print(f'Sigma: {sigma:.3f}')
    print(f'Alpha: {alpha:.3f}')
    print(f'Mean: {mean:.3f}')
    print(f'Standard Deviation: {std_dev:.3f}')

    #Нормальний розподіл
    def normal_distribution(x, mu, sigma):
        return 1 / (sigma * math.sqrt(2 * math.pi)) * math.exp(-((x - mu) ** 2) / (2 * sigma ** 2))

    #Підрахунок chi^2 
    observed, bin_edges = np.histogram(random_numbers, bins)
    expected = n * np.diff(bin_edges) * np.array([normal_distribution(x, alpha, sigma) for x in bin_edges[:-1]])
    degrees_of_freedom = len(observed) - 1 - 1 - 1
    chi2_statistic = np.sum((observed - expected) ** 2 / expected)
    critical_chi2 = chi2.ppf(1 - significance_level, degrees_of_freedom)

    print(f'Chi^2 Statistic: {chi2_statistic:.3f}')
    print(f'Critical Chi^2 Value: {critical_chi2:.3f}')
    print(f'-----------------------------------')

    #Гістограма випадкових чисел
    plt.hist(random_numbers, bins, density=True, alpha=0.8, color='g')

    #Графік нормального розподілу
    x = np.linspace(min(random_numbers), max(random_numbers), 1000)
    y = [normal_distribution(xi, alpha, sigma) for xi in x]
    plt.plot(x, y, 'r--')

    plt.xlabel('Random Numbers')
    plt.ylabel('Probability Density')
    plt.title(f'Generated Random Numbers vs. Normal Distribution (σ={sigma}, α={alpha})')
    plt.legend(['Expected Normal Distribution', 'Generated Random Numbers'])

plt.show()
