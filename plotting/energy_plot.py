import math
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

# Import all the constants shared across the files
from plot_constants import *


# Put in the directory of the csv
mesh_data = pd.read_csv(r'/home/myaccount/javastuff/admpm/ShakeWave/Static/test.csv', header=0)



# The analytic solution at hand
def analytic_solution(t):


	# Just a bunch of constants
	rho = 1.
	Y = 1.

	c = np.sqrt(Y/rho)

	L = 1.

	A = 0.01 # Velocity perturbation

	omega = 10.

	y = [] # Solution set


	# For all times, find the energy at each node
	for i in range(len(t)):


		if t[i] <= L/c:

			y.append( (0.5*rho * A**2 * c * (t[i] - 0.5 * np.sin(2*omega*t[i])/omega))/dx )

		else:

			temp = t[i]/2
			temp += 0.5*np.sin(omega*(t[i] - 2*L/c))*np.cos(omega*(3*t[i]-2*L/c))/omega
			temp -= np.sin(4*omega*(t[i]-L/c))/(8*omega)
			temp *= rho*A**2

			y.append( temp/dx )


	#end for


	return y


#end analytic_solution



energies = []

for i in range(  tsteps//record_frequency + 1 ):# The range function may need adjustment by \pm 1 depending on frequency


	rho = np.array(mesh_data['dens'][num_nodes*i : num_nodes*(i+1)])
	v = np.array(mesh_data['xvel'][num_nodes*i : num_nodes*(i+1)])
	eps = np.array(mesh_data['strain'][num_nodes*i : num_nodes*(i+1)])

	eners = 0.5 * (rho * v**2 + eps**2)


	ener = np.sum( eners )

	energies.append(ener)


#end for



data_times = mesh_data['time']



times = []

for i in range(0, len(data_times), num_nodes):


	times.append( data_times[i] )


#end for



rho = 1.
Y = 1.

c = np.sqrt(Y/rho)

L = 1.


plt.figure()


plt.plot(times, energies, '-', label='Measured Energy')
plt.plot(times, analytic_solution(times), '--', label='Analytic')
plt.xlabel( 'Time (s)' )
plt.ylabel( 'Energy/dx (Erg/dx)' )
plt.title( 'Energy v Time' )
plt.xlim([0, 2*L/c])
plt.ylim([0, 0.02])
plt.legend()




plt.show()
