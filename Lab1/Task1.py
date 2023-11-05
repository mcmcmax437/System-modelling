import random
import math
import matplotlib.pyplot as plt
import numpy as np
from scipy.stats import chi2

n = 10000
lambdas = [0.5, 1.0, 2.0]
significance_level = 0.05
bins = 20

for lam in lambdas:
    random_numbers = []

    # Генератор(експоненціальне випадкове число)
    for _ in range(n):
        Xi = random.random()

        exponential_random = -(1 / lam) * math.log(Xi)
        random_numbers.append(exponential_random)

    mean = np.mean(random_numbers)
    std_dev = np.std(random_numbers)

    print(f'Lambda (λ) = {lam}')
    print(f'Mean: {mean:.3f}')
    print(f'Standard Deviation: {std_dev:.3f}')

    #Побудова графіку для згенерованих чисел 
    plt.hist(random_numbers, bins, density=True, alpha=0.8, label=f'λ = {lam}')

    #Значення x для теоретичного PDF і розраховуємо його для лямбди (Probability Density Function)
    x = np.linspace(0, max(random_numbers), 20)
    pdf = lam * np.exp(-lam * x)

    plt.plot(x, pdf, label=f'Theoretical PDF (λ = {lam})', linewidth=2)

    plt.title(f'Exponential Distribution (λ = {lam}')
    plt.xlabel('Random Number')
    plt.ylabel('Probability Density')
    plt.legend()

    # Підрахунок chi^2 
    observed, _ = np.histogram(random_numbers, bins, density=True)
    expected = pdf * np.sum(np.diff(x)) 
    chi_square_statistic = np.sum((observed - expected) ** 2 / expected)
    degrees_of_freedom = len(observed) - 1 - 1 
    critical_chi_square = chi2.ppf(1 - significance_level, degrees_of_freedom)

    print(f'Chi-Square Statistic: {chi_square_statistic:.3f}')
    print(f'Critical Chi-Square Value: {critical_chi_square:.3f}')
    print(f'---------------------------------')
   

    plt.show()
