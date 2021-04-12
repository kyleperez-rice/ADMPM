import java.util.List; // Get lists

// Class that allows us to initialize the nodes/ material points of
// our system
public class Initializations {
	

	// Initialize our mesh, given number of node points
	// geometry, and step size
	public static void InitializeMesh(
		List<Node> nodes
	) {


		// Starting with an empty list,
		for (int i = 0; i < Constants.num_nodes; ++i) {


			// Initialize length (immutable mostly)
			nodes.get(i).length = Constants.dx;

			// Set the position based on our initial xlowbnd
			nodes.get(i).xpos = Constants.xlowbnd + (double)i*Constants.dx;

			// Set density, etc from the initial state
			nodes.get(i).dens = InitialState.Density( nodes.get(i).xpos );
			nodes.get(i).pres = InitialState.Pressure( nodes.get(i).xpos );
			nodes.get(i).gamma = InitialState.gamma( nodes.get(i).xpos );

			nodes.get(i).stress = -nodes.get(i).pres;
			nodes.get(i).xvel = InitialState.XVelocity( nodes.get(i).xpos );


		}//end for


	}//end InitializeMesh



	// Initialize our material points, using our nodes,
	// how many material points per cell we have, and our step size
	public static void InitializeMaterialPoints(
		List<MaterialPoint> mp_points,
		List<Node> nodes
	) {


		// Generate exactly how many material points we need
		// Note: this is the proper size. We don't have any mps
		// before the first node, or after the second one
		// this means we have nodes.size() - 1 # of cells
		for (int i = 0; i < Constants.num_particles; ++i) {


			// Set material point length
			mp_points.get(i).length = Constants.dx/(double)Constants.num_mps;

			// Set the material point position
			// Check the limiting cases:
			// i = 0 --> xpos = length/2   GOOD
			// i = (nodes.size - 1)*(num_mps-1) --> xpos = (nodes.size-1)*dx - 0.5*length   GOOD
			//
			// HOW THIS SETUP LOOKS:
			//
			//		dx: node size
			// ________________________________________
			// 0									   1
			// |----O----:----O----:----O----:----O----|----O----:----O...
			//      0		  1			2		  3			4		  5
			//		___________
			//			dx/num_mps: Material Point Length
			//
			// Key:
			// |	--> Location of node and end of a material point
			// O	--> Material Point xpos
			// :	--> Shared material point boundary, where both terminate in length
			// -	--> Area in a material point
			mp_points.get(i).xpos = ( (double)i + 0.5 ) * mp_points.get(i).length;


			// Start initializing material point quantities
			mp_points.get(i).mass = 0.;
			mp_points.get(i).xvel = 0.;
			mp_points.get(i).stress = 0.;
			mp_points.get(i).pres = 0.;
			//mp_points.get(i).ymodulus = 0.; // In case you don't have constant Y


		}//end for


		// Get the nearest nodes of the system
		MPMMath.GetNearNodes(mp_points);


		// Then use the nearest nodes to populate the material point quantities
		for ( int i = 0; i < Constants.num_particles; ++i ) {


			int left_nn = mp_points.get(i).left_nn;
			int right_nn = mp_points.get(i).right_nn;


			// Mass
			mp_points.get(i).mass += nodes.get( left_nn ).dens * nodes.get( left_nn ).shapef( mp_points.get(i).xpos );
			mp_points.get(i).mass += nodes.get( right_nn ).dens * nodes.get( right_nn ).shapef( mp_points.get(i).xpos );
			mp_points.get(i).mass *= mp_points.get(i).length;


			// Velocity
			mp_points.get(i).xvel += nodes.get( left_nn ).xvel * nodes.get( left_nn ).shapef( mp_points.get(i).xpos );
			mp_points.get(i).xvel += nodes.get( right_nn ).xvel * nodes.get( right_nn ).shapef( mp_points.get(i).xpos );


			// Strain
			mp_points.get(i).pres += nodes.get( left_nn ).pres * nodes.get( left_nn ).shapef( mp_points.get(i).xpos );
			mp_points.get(i).pres += nodes.get( right_nn ).pres * nodes.get( right_nn ).shapef( mp_points.get(i).xpos );


			// Young's Modulus
			mp_points.get(i).gamma = InitialState.gamma( mp_points.get(i).xpos );


			// Stress
			mp_points.get(i).stress = -mp_points.get(i).pres;


		}//end for


	}//end InitializeMaterialPoints


}//end Initializations
