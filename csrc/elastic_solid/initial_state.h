#ifndef INITIAL_STATE_H
#define INITIAL_STATE_H

// We assume that we know the initial state perfectly as a function
#include <math.h>
#include "solver_options.h"



//t = 0 Density
double init_dens(double x);

//t = 0 strain of the system
double init_strain(double x);

//t = 0 velocity
double init_xvel(double x);

//Young's Modulus of the system
double init_ymodulus();

//Initial stress of the system, from sigma = Y*strain
//double init_stress(double x);


#endif
