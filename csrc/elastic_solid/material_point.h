#ifndef MATERIAL_POINT_H
#define MATERIAL_POINT_H



struct material_point {


	// Std attributes
	double length;// Length of the mp


	// Position and physical velocity
	double xpos;
	double pnt_xvel;// IE: the physical velocity of a material point
		

	// Material Properties
	double xvel;
	double stress;
	double strain;


	//Mp weights
	double mass;


	//Other properties
	double ymodulus;// Young's Modulus


	// The index of the two nearest nodes to a material point
	int left_nn;
	int right_nn;


};//end material_point

#endif
