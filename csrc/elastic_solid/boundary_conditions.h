#ifndef BOUNDARY_CONDITIONS_H
#define BOUNDARY_CONDITIONS_H

#include <stdio.h>
#include <math.h>
#include "solver_options.h"
#include "node.h"


// Set boundary conditions to the velocity
void set_boundary(char type, struct node nodes[], double t);


#endif