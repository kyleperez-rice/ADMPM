# Contains global variables that apply to all my python programs here
# See solver_options.h / Constants.java for the corresponding numbers too!

# Geometry
num_nodes = 401

xlowbnd = 0.
xhighbnd = 1.

dx = (xhighbnd - xlowbnd)/(num_nodes-1) # Step size


# Particle properties
num_mps = 2


record_frequency = 4000 # How often the progrma records data

# Time
tmax = 0.5
tsteps = 400000

dt = tmax/tsteps
