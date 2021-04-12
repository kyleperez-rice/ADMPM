#ifndef DATA_WRITE_H
#define DATA_WRITE_H

#include <stdio.h>
#include <stdlib.h>//IE: make files
#include "solver_options.h"
#include "material_point.h"
#include "node.h"


// Just write the header of a file
void write_header(
	FILE *myFile,
	char* myTitles[]
);



//Sends the mesh data to a file; used for analysis
void node_data_write(
	FILE *nodeData,
	struct node nodes[],
	double t
);




//Write all of the important mp quantities out to a file
void mp_data_write(
	FILE *mpData,
	struct material_point mps[],
	double t
);


#endif
