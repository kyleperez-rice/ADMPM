#ifndef ACCELERATIONS_H
#define ACCELERATIONS_H

#include "node.h"



//Describes the external accelerations acting on the system
//	Takes in a node as an argument so we can easily implement
//	external forces that depend on velocity, etc
double extern_accel_x(struct node n, double t) {


	return 0.;


}


#endif