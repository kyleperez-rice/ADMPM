import java.util.*; // Get lists, etc


/*
	Contains:
		class MPMMath
			(Functions/Classes that perform vital mathematical operations to update)
			(the system)

			function: GetNearNodes
				(Gets the 2 nearest nodes to a material point)
*/

// A math class that contains methods useful to solving via MPM
public class MPMMath {


	// A fast way to get the two nodes nearest to a material point
	// given an equally spaced mesh
	//
	// Store the result in our left_nn and right_nn class elements
	//
	// How it works: send [a,b] --> [0, n-1] where n = mps.size()
	// Use same mapping on mps.xpos; produces a number between x, y
	// x,y integers
	// ceiling/floor xpos to get these integers, which are the 
	// nearest nodes to our material point
	public static void GetNearNodes(
		List<MaterialPoint> mps
	) {


		for ( int i = 0; i < mps.size(); ++i ) {


			// Double that's between the two nearest nodes
			double approx_index = (mps.get(i).xpos - Constants.xlowbnd)/Constants.dx;

			// The nerest nodes
			// Java's floor functions map from double to double?
			// Make no sense
			mps.get(i).left_nn = (int)Math.floor( approx_index );
			mps.get(i).right_nn = (int)Math.ceil( approx_index );


		}//end for


	}//end GetNearNodes


}//end MPMMATH