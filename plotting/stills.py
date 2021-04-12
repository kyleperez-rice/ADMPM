import math
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

from plot_constants import *


# Read data
staticnodes = pd.read_csv(r'/home/folder/javastuff/admpm/SimpShock/test_static.csv', header=0)
movenodes = pd.read_csv(r'/home/folder/javastuff/admpm/SimpShock/test_move.csv', header=0)





# Make various plots still plots
plt.figure(0)

i = 70
t = record_frequency * i * dt
xm = np.array(movenodes['xpos'][num_nodes*i : num_nodes*(i+1)])
ym = np.array(movenodes['xvel'][num_nodes*i : num_nodes*(i+1)])


plt.plot(xm, ym, '-')
plt.xlabel('x (cm)')
plt.ylabel('Velocity (cm/s)')
plt.xlim([0, 1])
plt.ylim([0, 0.0011])
plt.title('Velocity v x at t = ' + str(t) + ' (s)')






plt.figure(1)

i = 0
t = record_frequency * i * dt
xm = np.array(movenodes['xpos'][num_nodes*i : num_nodes*(i+1)])
ym = np.array(movenodes['strain'][num_nodes*i : num_nodes*(i+1)])

plt.plot(xm, ym, '-')
plt.xlabel('x (cm)')
plt.ylabel('Strain')
plt.xlim([0, 1])
plt.ylim([-0.0012, 0.0002])
plt.title('Strain v x at t = ' + str(t) + ' (s)')



plt.figure(2)

i = 70
t = record_frequency * i * dt
xm = np.array(movenodes['xpos'][num_nodes*i : num_nodes*(i+1)])
ym = np.array(movenodes['dens'][num_nodes*i : num_nodes*(i+1)])

plt.plot(xm, ym, '-')
plt.xlabel('x (cm)')
plt.ylabel('Density g/cm**3')
plt.xlim([0, 1])
plt.ylim([1-0.0012, 1+0.0012])
plt.title('Density v x at t = ' + str(t) + ' (s)')






plt.figure(3)

i = 70
t = record_frequency * i * dt
xm = np.array(staticnodes['xpos'][num_nodes*i : num_nodes*(i+1)])
ym = np.array(staticnodes['xvel'][num_nodes*i : num_nodes*(i+1)])


plt.plot(xm, ym, '-')
plt.xlabel('x (cm)')
plt.ylabel('Velocity (cm/s)')
plt.xlim([0, 1])
plt.ylim([0., 0.0011])
plt.title('Velocity v x at t = ' + str(t) + ' (s)')






plt.figure(4)

i = 70
t = record_frequency * i * dt
xm = np.array(staticnodes['xpos'][num_nodes*i : num_nodes*(i+1)])
ym = np.array(staticnodes['strain'][num_nodes*i : num_nodes*(i+1)])

plt.plot(xm, ym, '-')
plt.xlabel('x (cm)')
plt.ylabel('Strain')
plt.xlim([0, 1])
plt.ylim([-0.0012, 0.0002])
plt.title('Strain v x at t = ' + str(t) + ' (s)')





plt.show()
