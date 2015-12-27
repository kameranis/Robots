import matplotlib.pyplot as plt
import numpy as np

x = [453, 2785, 17464, 486579, 950875, 30102585]
y = [0.191, 0.240, 0.355, 0.722, 1.034, 28.241]
plt.plot(x, y)
plt.xlabel("# of Nodes processed")
plt.ylabel("Time (s)");
plt.title("Admissible heiuristic");
plt.show()



