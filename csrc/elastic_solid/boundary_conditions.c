#include "boundary_conditions.h"



// Includes the boundary conditions necessary to a given problem
void set_boundary(char type, struct node nodes[], double t) {


    // IE: v = velocity
    if (type == 'v') {

        // Set the xvelocity at the two boundary nodes
        nodes[0].xvel = 0.;
        nodes[ num_nodes - 1 ].xvel = 0.01 * sin( 10. * t );
        //nodes[ num_nodes - 1].xvel = 0.;

    }//end if


}//end set_boundary