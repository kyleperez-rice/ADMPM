#include "data_write.h"



//This function outputs the CSV we use for data analysis
//	Okay, this one is really hardcoded; we should have 2 different
//	functions that write each respective header.
//	But in this case, our titles are all the same length.
void write_header(
	FILE *myFile,
	char* myTitles[]
) {


	for ( int i = 0; i < nd_titles_size; ++i ) {


		fprintf( myFile, "%s", myTitles[i] );

		if ( i != nd_titles_size-1 ) {

			fprintf( myFile, "," );

		}
		else {

			fprintf( myFile, "\n" );

		}//end if


	}//end for


}//end write_header




// Write the node data for a given time
void node_data_write(
	FILE *nodeData,
	struct node nodes[],
	double t
) {


	//Actually writing the data
	//Basically just go through all the class elements of the node and save them
	for (int i = 0; i < num_nodes; ++i) {
		

		fprintf( nodeData, "%f,", t );
		fprintf( nodeData, "%f,", nodes[i].xpos );
		fprintf( nodeData, "%f,", nodes[i].stress );
		fprintf( nodeData, "%f,", nodes[i].strain );
		fprintf( nodeData, "%f,", nodes[i].dens );
		fprintf( nodeData, "%f\n", nodes[i].xvel );
		

	}//end for


}//end node_data_write






// Write the material point data for a given time
void mp_data_write(
	FILE *mpData,
	struct material_point mps[],
	double t
) {


	//Actually writing the data
	//Basically just go through all the class elements of the node and save them
	for (int i = 0; i < num_particles; ++i) {
		
		
		fprintf( mpData, "%f,", t );
		fprintf( mpData, "%f,", mps[i].xpos );
		fprintf( mpData, "%f,", mps[i].mass );
		fprintf( mpData, "%f,", mps[i].stress );
		fprintf( mpData, "%f,", mps[i].strain );
		fprintf( mpData, "%f\n", mps[i].xvel );


	}//end for


}//end node_data_write