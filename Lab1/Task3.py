import matplotlib.pyplot as plt
import numpy as np
from scipy import stats

bins = 200
params = [
    (5**13, 2**31),
    (7**14, 4**33),
    (10**15, 9**29)
]
n = 10000  
significance_level = 0.95

for i, (a, c) in enumerate(params):
    # Ініціалізація порожнього списку
    z_i = 1
    random_numbers = []

    # Генератор
    for _ in range(n):
        z_i = (a * z_i) % c
        x_i = z_i / c
        random_numbers.append(x_i)

    mean = np.mean(random_numbers)
    std_dev = np.std(random_numbers)
    
    print(f'Mean: {mean:.3f}')
    print(f'Standard Deviation: {std_dev:.3f}')

    # Підрахунок chi^2 
    observed, _ = np.histogram(random_numbers, bins)
    expected = np.full(bins, n / bins)
    chi2, p = stats.chisquare(observed, f_exp=expected)
    degrees_of_freedom = len(observed) - 1 - 1 - 1
    critical_chi2 = stats.chi2.ppf(significance_level, degrees_of_freedom) 

    print(f'Chi^2 Statistic: {chi2:.3f}')
    print(f'Critical Chi^2 Value ({significance_level}): {critical_chi2:.3f}')
    print(f'-----------------------------------')

    # Побудова гістограми
    plt.hist(random_numbers, bins, density=True, alpha=0.8, color='g', label=f'Random Numbers ({i+1})')
    plt.xlabel('Random Number')
    plt.ylabel('Frequency')
    plt.title(f'Random Number Distribution ({i+1})')

    # Побудуйте щільність розподілу для рівномірного розподілу на інтервалі (0, 1)
    x = np.linspace(0, 1, 100)
    plt.plot(x, np.ones_like(x), 'r', label='Uniform PDF (0-1)')
    plt.xlabel('Random Numbers')
    plt.ylabel('Probability Density')
    
    plt.show()
