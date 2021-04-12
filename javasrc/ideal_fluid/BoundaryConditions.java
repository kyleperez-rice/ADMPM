/*
	Contains:
		class: BoundaryConditions
			(Contains the boundary conditions of the system)

			class: Velocity
				(boundary conditions on the velocity)

				function: Left
					(bcs on the left side of the system)
				function: Right
					(bcs on the left system of the system)
					
			(unused template for more conditions on the system)

*/



// Class that defines the boundary conditions on the system
public class BoundaryConditions {


	// Defines the boundary conditions on the velocity
	public static class Velocity {


			// Left boundary condition
			public static double Left( double t ) {

				return 0.;

			}//end Left
			

			// Right boundary condition
			public static double Right( double t ) {

				return 0.01 * Math.sin(10. * t);

			}//end Right


	}//end Velocity



	// Boundary conditions on some other condition
	/*
	public static class Quantity {


		// Left boundary condition
		public static double Left( double t ) {

			return 0.;

		}//end Left
		

		// Right boundary condition
		public static double Right( double t ) {

			return 0.;

		}//end Right


	}//end Quantity
	*/


}//end BoundaryConditions