/*
	Contains:
		class: Accelerations
			(defines external accelerations on the system)

			function: ext_x
				(External acceleration in the x direction)
*/

// Defines the external accelerations acting on a node in a given direction
//	We use a Node as a parameter since in theory we could have a velocity
// 	dependent acceleration (like a magnetic field)
public class Accelerations {


	// static makes sure we don't have to make a new class member for this system to call function
	public static double external_x( Node n, double t ) {


		return 0.;


	}//end external_x



}//end Accelerations
