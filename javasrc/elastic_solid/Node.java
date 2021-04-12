/*
	Contains:
		class: Node
			(describes a node in the system)

			time
			length
			xpos
			dens
			mass
			xvel
			l-xvel
			stress
			strain
			ymodulus
			function: shapef
				(linear shape function for a system)
			function: d_shapef
				(derivative of shapef)


*/

// Declares the 'Node' class
// which tells us about the elements our partition of the area we're looking at
public class Node {


	// Attributes of a node
	public double length; // Length of the node

	public double xpos;
	//public double ypos;
	//public double zpos;


	//Quantities that change over time
	public double dens; // Density
	public double mass; // Mass of node

	public double xvel; // x velocity


	public double stress;
	public double strain;



	//Continua-dependent properties
	public double ymodulus; // Young's Modulus



	//Shape function of a node and its derivative
	public double shapef( double x ) {


		// This defines the upper and lower points of thed shpae function
		// At x = xpos, we should have shapef = 1
		// And at x = xpos \pm length, shapef = 0
		// In our case, we use a simple linear interpolation between points
		double lpoint = this.xpos - this.length;
		double rpoint = this.xpos + this.length;

		if ( x > lpoint && x <= xpos ) {

			return 1. + (x - this.xpos)/this.length;

		}
		else if ( x > xpos && x < rpoint ) {

			return 1. - (x - this.xpos)/this.length;

		}
		else {

			return 0.;

		}//end if


	}//end shapef


	public double d_shapef( double x ) {


		// Just the analytic derivative of shapef
		// At the discontinuity, we take the average value of each side
		double lpoint = this.xpos - this.length;
		double rpoint = this.xpos + this.length;

		if ( x > lpoint && x < xpos ) {

			return 1./this.length;

		}
		else if ( x > xpos && x < rpoint ) {

			return -1./this.length;

		}
		else if ( x == lpoint ) {

			return 0.5/this.length;

		}
		else if (x == rpoint ) {

			return -0.5/this.length;

		}
		else {

			return 0.;

		}//end if


	}//end d_shapef








}//end node
