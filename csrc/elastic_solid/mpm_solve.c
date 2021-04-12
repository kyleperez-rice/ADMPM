//Standard Includes
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h> // for benchmarking

// Constants and options
#include "solver_options.h"

//Custom Built Functions that contain necessary definitions
#include "mpm_math.h"

//Class definitions that define the mesh and the material points
#include "node.h"
#include "material_point.h"

//Information about the initial State
#include "initial_state.h"

//Information about the external accelerations of the system
#include "accelerations.h"

//Functions that let us initialize the mesh and material points
#include "initializations.h"

//Functions for advancing the system in time
#include "sim_update.h"//For density, etc

//Data Exportation and Utility
#include "data_write.h"//Functions on writing data to a CSV



int main() {


// Time
	double time_spent = 0.;
	clock_t begin = clock();

// Initial simulation setup

	// The time
	double t = 0.;

	//Generic elements of classes
	struct node nodes[num_nodes];
	struct material_point mp_points[num_particles];
	
	

//------------------------------------------------------------------------------------
// Data Setup



	// Create output data files
	FILE *nodeData;
	FILE *mpData;
	FILE *debugFile;

	nodeData = fopen( node_filename, "w+" );
	mpData = fopen( mp_filename, "w+" );


	// Write header for the output files
	write_header( nodeData, node_titles );
	write_header( mpData, mp_titles );



	
	
//--------------------------------------------
//INITIALIZATION


	// First initialize the mesh
	// Then use the mesh to initialze the material points
	//	In the material points initializiation, we find the nearest nodes
	//	to each particles, which we fix for the whole program.
	//		See: initializations.c
	//			 initial_state.c
	initialize_mesh(nodes);
	initialize_material_points(mp_points, nodes);
	

	// Using the material points to approximate an integral to compute the mass, 'inverse mass'
	// and Y equivalent for use in time derivatives
	//		See sim_update.c
	compute_node_masses(nodes, mp_points);





//TIME EVOLUTION
		
	for (int tt = 0; tt < tsteps; ++tt) {


		if (tt % record_frequency == 0) {

			// Write the data
			node_data_write( nodeData, nodes, t );
			mp_data_write( mpData, mp_points, t );

		}//end if
		
		
		// Then evolve the system
		//
		//
		// First update the strain in time
		//
		// Then the velocity:
		// Given the velocity at one moment of time, advances it to the next timestep
		// Does:
		//	1. Partially calculate the mp velocity at the next time
		//	2. Calculate the lagr xvel of a node
		//	3. Calulate the physical velocity of the particles
		//	4. Finishes calculating the mp velocity at the next time
		//	5. (optional) recomupute particle positions + node masses
		//	6. Update the node velocity using the new mp velocities
		//
		// Then update the stress
		update_particle('e', mp_points, nodes);
		update_velocity( nodes, mp_points, t );
		update_particle('s', mp_points, nodes);
		



		
		//Update the other mesh quantities
		// See: sim_update.c
		//	Denisty
		//	Strain
		//	Stress
		update_node('r', nodes, mp_points);
		update_node('e', nodes, mp_points);
		update_node('s', nodes, mp_points);


		// Advance the system in time
		t += dt;
	
		
	}//end for
	
//--------------------------------------------------------


//-------------------------------------------------------
//Finshing up writing data


	node_data_write( nodeData, nodes, t );
	mp_data_write( mpData, mp_points, t );
	fclose(nodeData);
	fclose(mpData);
	


	// Print the time elapsed
	clock_t end = clock();

	time_spent += (double)(end-begin) / CLOCKS_PER_SEC;

	printf("Time elapsed: %f seconds\n", time_spent);

	return 0;


}//end main
