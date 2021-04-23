#ifndef BOUNDARY_CONDITIONS_H
#define BOUNDARY_CONDITIONS_H

#include <stdio.h>
#include <math.h>
#include "solver_options.h"
#include "node.h"


// Set boundary conditions to the velocity
void set_boundary(char type, struct node nodes[], double t) {


    // IE: v = velocity
    if (type == 'v') {

        // Set the xvelocity at the two boundary nodes
        nodes[0].xvel = 0.;
        nodes[ num_nodes - 1 ].xvel = 0.01 * sin( 10. * t );
        //nodes[ num_nodes - 1].xvel = 0.;

    }//end if


}//end set_boundary


#endif