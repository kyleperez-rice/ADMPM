import math
import pandas as pd
import matplotlib.pyplot as plt
from matplotlib import animation
import numpy as np

from plot_constants import *


# Import the data; put in your own path to the csv
mesh_data = pd.read_csv(r'/home/folder/javastuff/admpm/SimpShock/test_move.csv', header=0)



# Define an analytic solution
#	This one isn't for anything special
#	Fill it in yourself!
def analytic_solution( type, x, t ):


	# Put parameters here
	rho = 1.
	Y = 1.

	c = np.sqrt(Y/rho)


	y = [] # The analytic solution at a given time


	if ( type == 'Strain' ):

		y.append( c * t * x[i])

	elif ( type == 'Density' ):

		y.append( c * t * x[i] )

	elif ( type == 'Xvel' ):

		y.append( c * t * x[i] )

	#end if


#end analytic_solution





# Needed for animation
fig = plt.figure()
ax = fig.add_subplot(1,1,1)



# The animation update function
#	Templates set forth for strain, dens, etc
def update(i):


	x = np.array(mesh_data['xpos'][num_nodes*i : num_nodes*(i+1)])
	
	#y = mesh_data['strain'][num_nodes*i: num_nodes*(i+1)]
	#y = mesh_data['dens'][num_nodes*i: num_nodes*(i+1)]
	y = mesh_data['xvel'][num_nodes*i: num_nodes*(i+1)]

	time = i * dt * record_frequency
	
	ax.clear()
	ax.plot(x, y, '-', label='Simulation')

	# Want analytic solution? Make a good one and use these lines of code
	#ax.plot(x, analytic_solution('Strain', x, time), '--', label='Analytic')
	#ax.plot(x, analytic_solution('Density', x, time), '--', label='Analytic')
	#ax.plot(x, analytic_solution('Xvel', x, time), '--', label='Analytic')

	ax.legend()
	

	# Axes
	#Works for Stress/Strain
	#ax.set(ylim = [-0.0015, 0.0005], xlim=[xlowbnd,xhighbnd], xlabel='x (cm)', ylabel='Strain',title='Strain v x')
	
	#Works for Density
	#ax.set(ylim=[0.999, 1.001], xlim=[xlowbnd,xhighbnd], xlabel='x (cm)', ylabel='Density (g/cm**3)',title='Density v x')
	
	#Works for xvel
	ax.set( ylim=[-0.001, 0.001], xlim=[xlowbnd,xhighbnd], xlabel='x (cm)', ylabel='Velocity (cm/s)',title='Velocity v x')
	

#end animation
	
# the frames=... num can prevent the plot from crashing
ani = animation.FuncAnimation(fig, func=update, frames=tsteps//record_frequency, interval=10, save_count = tsteps//record_frequency)


# Saving the animation
#	Needs the ffmpeg writer to begin with!
Writer = animation.writers['ffmpeg']
writer = Writer(fps=15, metadata=dict(artist='Me'), bitrate=1800)
video = animation.FFMpegWriter(fps=10)
ani.save('coolanimation.mp4', writer=writer)




plt.show()


