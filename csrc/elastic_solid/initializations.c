#include "initializations.h"



//Initialize the mesh
	//In particular, set how many points there are, and the distance between nodes.
	//Then use the initial state of the system to build the mesh quantities.
void initialize_mesh( struct node nodes[] ) {
	
	
	//Initialize each element
	for (int i = 0; i < num_nodes; ++i) {
	
		
		//Initialize its length
		nodes[i].length = dx;

		//Set its position
		nodes[i].xpos = xlowbnd + (double)i*dx;//Initial node position
		
		
		//Set density, etc from initial distributions of the system
		nodes[i].dens = init_dens(nodes[i].xpos);
		nodes[i].strain = init_strain(nodes[i].xpos);
		nodes[i].ymodulus = init_ymodulus();

		nodes[i].stress = nodes[i].ymodulus * nodes[i].strain;
		nodes[i].xvel = init_xvel(nodes[i].xpos);
	
	
	}//end for


}//end initialize_mesh





//Use the just initialized nodes to build your material points.
	//Take how many mps you should have between two nodes and use your nodes to initialize positions/attributes of mps.
void initialize_material_points(
	struct material_point mp_points[],
	struct node nodes[]
) {


	//Initialize material points
	for ( int i = 0; i < num_particles; ++i ) {


		mp_points[i].length = dx/(double)num_mps;

		//Set its position
		//	Check limiting cases:
		//	i = 0 --> xpos = .length/2 GOOD
		//	i = meshp.size()*nmps-1 --> xpos = dx*meshp.size() - 0.5*.length GOOD
		//
		//	HOW THIS SETUP LOOKS:
		//
		//			dx: mesh point size
		//	_________________________________________
		//	0					1
		//	|----O----:----O----:----O----:----O----|----O----:----O...
		//	     0         1         2         3         4         5
		//		  ___________
		//	   	       dx/num_mps: Material Point length
		//
		//
		//	KEY:
		//	|	-->	Location of Meshpoint cell and end of a material point's length
		//	O	-->	Material Point xpos
		//	:	-->	Shared Material Point boundary, where both terminate in length
		//	-	-->	Area in a material point
		mp_points[i].xpos = ((double)i + 0.5)*mp_points[i].length;

		mp_points[i].mass = 0.;
		mp_points[i].xvel = 0.;
		mp_points[i].stress = 0.;
		mp_points[i].strain = 0.;

		//mp_points[i].ymodulus = 0.;


	}//end for


	// Find the nearest nodes to the system
	get_near_nodes( mp_points );


	// Then populate the material point quantities using our near nodes
	for (int i = 0; i < num_particles; ++i) {


		int left_nn = mp_points[i].left_nn;
		int right_nn = mp_points[i].right_nn;


		// Mass
		mp_points[i].mass += nodes[ left_nn ].dens * shapef( mp_points[i].xpos, nodes[left_nn] );
		mp_points[i].mass += nodes[ right_nn ].dens * shapef( mp_points[i].xpos, nodes[right_nn] );
		mp_points[i].mass *= mp_points[i].length;


		// Velocity
		mp_points[i].xvel += nodes[ left_nn ].xvel * shapef( mp_points[i].xpos, nodes[left_nn] );
		mp_points[i].xvel += nodes[ right_nn ].xvel * shapef( mp_points[i].xpos, nodes[right_nn] );


		// Strain
		mp_points[i].strain += nodes[ left_nn ].strain * shapef( mp_points[i].xpos, nodes[left_nn] );
		mp_points[i].strain += nodes[ right_nn ].strain * shapef( mp_points[i].xpos, nodes[right_nn] );
	

		// Young's Modulus
		mp_points[i].ymodulus = init_ymodulus();
		

		// Stress
		mp_points[i].stress = mp_points[i].strain * mp_points[i].ymodulus;
	
	
	}//end for
	


}//end initialize_material_points
