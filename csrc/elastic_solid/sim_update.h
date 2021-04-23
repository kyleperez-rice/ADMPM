#ifndef SIM_UPDATE_H
#define SIM_UPDATE_H

#include <stdio.h>
#include "solver_options.h"
#include "node.h"
#include "material_point.h"
#include "accelerations.h"
#include "mpm_math.h"
#include "boundary_conditions.h"// Include the boundary conditions



// Compute the masses of the nodes
void compute_node_masses(
	struct node nodes[],
	struct material_point mp_points[]
) {


	for ( int i = 0; i < num_nodes; ++i ) {


		nodes[i].mass = 0.;


	}//end for


	for ( int i = 0; i < num_particles; ++i ) {


		int left_nn = mp_points[i].left_nn;
		int right_nn = mp_points[i].right_nn;

		nodes[ left_nn ].mass += mp_points[i].mass * shapef( mp_points[i].xpos, nodes[left_nn] );
		nodes[ right_nn ].mass += mp_points[i].mass * shapef( mp_points[i].xpos, nodes[right_nn] );


	}//end for


}//end compute_node_masses




// Move the particles in time
void move_particles( struct material_point mps[] ) {


	for ( int i = 0; i < num_particles; ++i ) {


		mps[i].xpos += mps[i].pnt_xvel*dt;


	}//end for


}//end move_particles




// Does the following:
// q_kl^L = q_kl + dq_kl/dt * dt
//
// Uses the approximation of
// 	dq_kl/dt = f_kl
//		   -1/(m_kl) * Sum_{p: all mps} (vol_p * stress_k(x_p) * d_shapef_l(x_p))
//		   +1/m_kl * Integral_{Boundary of V} stress_k * shapef_l(x) dS
//
// Does these calculations for every mesh point to calculate their lagrangian quantity for a given
// type
void update_velocity(
	struct node nodes[],
	struct material_point mps[],
	double t
) {


	// First, partially update as v_p = v_p - sum_{l: nodes} v_l S_l(x_p)
	for ( int i = 0; i < num_particles; ++i ) {


		int left_nn = mps[i].left_nn;
		int right_nn = mps[i].right_nn;

		mps[i].xvel -= nodes[ left_nn ].xvel * shapef( mps[i].xpos, nodes[left_nn] );
		mps[i].xvel -= nodes[ right_nn ].xvel * shapef( mps[i].xpos, nodes[right_nn] );


	}//end for


	// Then, compute the Lagr velocity v^L = v + dv/dt * dt
	// This corresponds to
	// v^L = v^n + f*dt - dt/m * sum_{p: mps} v_p sigma_p * d_shapef( mp xpos )
	//
	// First include the internal forces
	for ( int i = 0; i < num_nodes; ++i ) {


		nodes[i].xvel += extern_accel_x( nodes[i], t )*dt;


	}//end for




	// Then include the material point contributions
	// Sum over all material points, but only use the nodes closest to a material point
	for ( int i = 0; i < num_particles; ++i ) {


		int left_nn = mps[i].left_nn;
		int right_nn = mps[i].right_nn;

		nodes[ left_nn ].xvel -= mps[i].length * mps[i].stress * d_shapef( mps[i].xpos, nodes[left_nn] ) * dt/nodes[ left_nn ].mass;
		nodes[ right_nn ].xvel -= mps[i].length * mps[i].stress * d_shapef( mps[i].xpos, nodes[right_nn] ) * dt/nodes[ right_nn ].mass;


	}//end for


	// Enforce the boundary conditions!
	set_boundary('v', nodes, t);

	
	// Then compute the particle velocity from a total of
	// v_p(t+dt) = v_p(t) + sum_{l: nodes} (v^L - v^n)*shapef_l(xpos)
	//
	// We only need to add the sum_l v^L *shapef_l(xpos) term!
	//
	// Also, in the same loop, calculate the physical velocity of the material points
	// with pnt_xvel = sum_l v^L shapef_l(xpos)
	//
	// We can use this to write v_p(t+dt) = v_p(t)mod + pnt_xvel
	for ( int i = 0; i < num_particles; ++i ) {


		int left_nn = mps[i].left_nn;
		int right_nn = mps[i].right_nn;

		mps[i].pnt_xvel = nodes[ left_nn ].xvel * shapef( mps[i].xpos, nodes[left_nn] );
		mps[i].pnt_xvel += nodes[ right_nn ].xvel * shapef( mps[i].xpos, nodes[right_nn] );

		mps[i].xvel += mps[i].pnt_xvel;


	}//end for


	if ( moving_particles ) {

		// Next, move the material points and update the node masses
		move_particles( mps );
		compute_node_masses(nodes, mps);

	}//end if



	// Then compute the nodal velocity at the future time
	// Using v(t) = sum_{ p: material point } m_p * v_p * shapef_l(mp xpos) / mass_l
	for ( int i = 0; i < num_nodes; ++i ) {


		nodes[i].xvel = 0.;


	}//end for

	for ( int i = 0; i < num_particles; ++i ) {


		int left_nn = mps[i].left_nn;
		int right_nn = mps[i].right_nn;

		nodes[ left_nn ].xvel += mps[i].mass * mps[i].xvel * shapef( mps[i].xpos, nodes[left_nn] );
		nodes[ right_nn ].xvel += mps[i].mass * mps[i].xvel * shapef( mps[i].xpos, nodes[right_nn] );


	}//end for


	// Finally, divinde node 'xvelocities' by the node mass
	for ( int i = 0; i < num_nodes; ++i ) {


		nodes[i].xvel /= nodes[i].mass;


	}//end for


	// Final enforcing of boundary conditions
	set_boundary('v', nodes, t);


	


}//end update_velocity




//This uses the lagr_dif_sum function to find what we add to the current q value to
//advance the material point's velocity in time
//
// For Velocity:
//	Does: v_p(t + dt) = v_p(t) + Sum_{l: all nodes}(v_l^L - v_l)*shapef_l(x_p)
// 	for every material point
//
// For strain:
//	Does: strain_p(t + dt) = strain_p(t) + Sum:{l: all nodes}(v_l * shapef_l(x_p))
//
// NOTE:
//	You should call this function in the order of its IF statements
void update_particle(
	char type,
	struct material_point mps[],
	struct node nodes[]
) {


	// IE: strain (epsilon)
	if (type == 'e') {
	
		for (int i = 0; i < num_particles; ++i) {
		

			int left_nn = mps[i].left_nn;
			int right_nn = mps[i].right_nn;

			mps[i].strain += nodes[ left_nn ].xvel * d_shapef( mps[i].xpos, nodes[left_nn] ) * dt;
			mps[i].strain += nodes[ right_nn ].xvel * d_shapef( mps[i].xpos, nodes[right_nn] ) * dt;
		
		
		}//end for
	}
	// IE: stress
	else if (type == 's') {
	
		for (int i = 0; i < num_particles; ++i) {
		
			
			//Okay, this relies on the stress-strain relation. Not very general!
			// Would have to go back here to change the code for a different relation!
			mps[i].stress = mps[i].strain * mps[i].ymodulus;
		
		
		}//end for
		
	}//end if


}//end update_mp_quantity





// Goes from Material Point Quantities to node quantities
// Does a sum of the form:
// q_kl = 1/(m_kl) * Sum_{p: all mps} m_kp * q_kp * shapef_l(x_p)
// for every l in our mesh, given a particular type to advance
void update_node(
	char type,
	struct node nodes[],
	struct material_point mp_points[]
) {


	// IE: density (rho)
	if (type == 'r') {

		// Reset density = mass / length
		for ( int i = 0; i < num_nodes; ++i ) {


			nodes[i].dens = nodes[i].mass / nodes[i].length;


		}//end for
		
		// Boundary nodes are of half length!
		nodes[0].dens *= 2.;
		nodes[ num_nodes - 1 ].dens *= 2.;

	}
	// Strain (epsilon)
	else if (type == 'e') {

		// Reset strain
		for ( int i = 0; i < num_nodes; ++i ) {


			nodes[i].strain = 0.;


		}//end for


		for ( int i = 0; i < num_particles; ++i ) {


			int left_nn = mp_points[i].left_nn;
			int right_nn = mp_points[i].right_nn;

			nodes[ left_nn ].strain += mp_points[i].mass * mp_points[i].strain * shapef( mp_points[i].xpos, nodes[left_nn] );
			nodes[ right_nn ].strain += mp_points[i].mass * mp_points[i].strain * shapef( mp_points[i].xpos, nodes[right_nn] );


		}//end for

	
		for (int i = 0; i < num_nodes; ++i) {
		
		
			nodes[i].strain /= nodes[i].mass;
		
		
		}//end for

	}
	// Okay, don't actually need to do stress
	// Stress
	else if (type == 's') {
	
		for (int i = 0; i < num_nodes; ++i) {
		
		
			nodes[i].stress = nodes[i].strain * nodes[i].ymodulus;

		
		}//end for
	
	}//end if


}//end update_node

#endif
