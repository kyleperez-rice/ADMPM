#ifndef NODE_H
#define NODE_H



// Struct that holds mesh point properties
struct node {


	// Std attributes
	double length;


	// Positiions, etc
	double xpos;


	//Quantities
	double dens;
	double xvel;
	double stress;
	double strain;


	// Weights of a meshpoint
	double mass;


	// Other properties
	double ymodulus;// Young's Modulus


};//end node


// Node shape function + Derivatives



// Linear Shape functions for a given mesh point!
double shapef( double x, struct node nd) {

	
	// Defines the upper and lower points of the shape function
	// At x = xpos, we should have shapef == 1.
	// At x = xpos \pm length we should have shapef == 0
	double lpoint = nd.xpos - nd.length;
	double rpoint = nd.xpos + nd.length;
	
	// If x is in the left half
	if ( x > lpoint  && x <= nd.xpos ) {
	
		return 1. + (x-nd.xpos)/nd.length;
	
	}
	// If x is in the right half
	else if ( x > nd.xpos && x < rpoint ) {
	
		return 1. - (x-nd.xpos)/nd.length;
	
	}
	// Otherwise
	else {//Probably don't need else statement
	
		return 0.;
	
	}//end if

	// The sum of all these shape functions is 1.
	// It takes a little thinking to get here:
	//	Promote xpos to an indexed form: xpos_i
	//	shapef()_i = same but with xpos -> xpos_i
	// Sum of a half of a shape function is shapef_i + shapef_i+1 on a particular half
	// Compute sum, use the fact that xpos_i - xpos_i+1 = length
	// Done
	

}//end shapef





//Mesh Point's derivative of shape function
double d_shapef(double x, struct node nd) {


	//Just the analytic derivative of shapef
	//We take the average value of the left and right hand derivatives at xpos,
	//Where the derivative is discontinuous
	double lpoint = nd.xpos - nd.length;
	double rpoint = nd.xpos + nd.length;

	if (x > lpoint && x < nd.xpos) {

		return 1./nd.length;

	}
	else if (x > nd.xpos && x < rpoint) {

		return -1./nd.length;

	}
	else if (x == lpoint) {

		return 0.5/nd.length;

	}
	else if (x == rpoint) {

		return -0.5/nd.length;

	}
	else {

		return 0.;

	}//end if

	//These add to 0!
	
	
}//end d_shapef

#endif
