import matplotlib.pyplot as plt
import numpy as np

x = [621, 17836, 167940, 1225714, 20851468, 77702724]
y = [0.171, 0.255, 0.433, 1.175, 16.841, 142.26]
plt.plot(x, y)
plt.xlabel("# of Nodes processed")
plt.ylabel("Time (s)");
plt.title("Admissible heiuristic");
plt.show()

x = [191, 1916, 7088, 29513, 187821, 753175]
y = [0.151, 0.190, 0.309, 0.392, 0.686, 2.098]
plt.plot(x, y)
plt.xlabel("# of Nodes processed")
plt.ylabel("Time (s)");
plt.title("Non-Admissible heiuristic");
plt.show()
