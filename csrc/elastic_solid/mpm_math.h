#ifndef MPM_MATH_H
#define MPM_MATH_H

#include <stdio.h>
#include <math.h>
#include "solver_options.h"
#include "node.h"
#include "material_point.h"//need material_point class



// Given a material point set in an equally spaced 1d mesh,
// we can map our interval that describes the space
// [xmin, xmax] to [0, n-1], where n is the number of nodes
//
// The function of this mapping is f = (x-xmin)/dx
// Where dx = (n-1)/(xmax-xmin)
// We can use this to map a material point's x position to
// some place between [0, n-1], and thus use floor/ceilings to
// tells us the two closest nodes to a material point
void get_near_nodes( struct material_point mps[] ) {


	for ( int i = 0; i < num_particles; ++i ) {


		// A double that's between the two nearest nodes
		double approx_index = (mps[i].xpos - xlowbnd)/dx;

		// The nearest nodes
		mps[i].left_nn = (int)floor( approx_index );
		mps[i].right_nn = (int)ceil( approx_index );


	}//end for


}//end get_near_nodes




#endif
