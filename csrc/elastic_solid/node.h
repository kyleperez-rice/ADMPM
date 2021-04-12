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
double shapef( double x, struct node nd );
double d_shapef( double x, struct node nd );

#endif
