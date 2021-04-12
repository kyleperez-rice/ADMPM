#ifndef SIM_UPDATE_H
#define SIM_UPDATE_H

#include <stdio.h>
#include "solver_options.h"
#include "node.h"
#include "material_point.h"
#include "accelerations.h"
#include "mpm_math.h"
#include "boundary_conditions.h"// Include the boundary conditions



// Use the material points to compute the mass equivalents of the mesh points
// Does an approximation of a Riemann Sum!
void compute_node_masses(
	struct node nodes[],
	struct material_point mp_points[]
);


// Move the material points in time
void move_particles( struct material_point mps[] );



// Use dq/dt and the Eulerian q to find the Lagrangian q
//	Contains Differential Equations + Boundary Conditions
void update_velocity(
	struct node nodes[],
	struct material_point mp_points[],
	double t
);



// Use the Euler + Lagr mesh_point quantities to advance the material point quantities
// to the next timestep
void update_particle(
	char type,
	struct material_point mps[],
	struct node nodes[]
);




// Using the material point quantities at the next timestep, find the meshpoint quantity
// at that new timestep
void update_node(
	char type,
	struct node nodes[],
	struct material_point mp_points[]
);

#endif
