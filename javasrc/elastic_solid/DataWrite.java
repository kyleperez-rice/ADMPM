import java.io.*; // Gets us CSV output
import java.util.List; // Gets us lists

/*
	Contains:
		class: DataWrite
			(functions on writing data for analysis)
			
			function: MakeHeaders
				(write headers for csvs)
			function: Node
				(Writes out node data to a csv)
			function: MaterialPoint
				(writes out material point data to a csv)
*/


public class DataWrite {
	

	public static void MakeHeaders(
		FileWriter myFile,
		List<String> titles
	) throws IOException {


		for ( int i = 0; i < titles.size(); ++i ) {


			myFile.append( titles.get(i) );

			if ( i != titles.size() - 1 ) {

				myFile.append(',');

			}
			else {

				myFile.append('\n');

			}//end if


		}//end for


	}//end MakeHeaders


	// Writes node data to a file at a given time
	public static void Node(
		FileWriter nodeData,
		List<Node> nodes,
		double t
	) throws IOException{


		
		// Write the time and node quantities
		for ( int i = 0; i < Constants.num_nodes; ++i ) {


			nodeData.append( String.valueOf(t) );
			nodeData.append(',');
			nodeData.append( String.valueOf(nodes.get(i).xpos) );
			nodeData.append( ',' );
			nodeData.append( String.valueOf(nodes.get(i).stress) );
			nodeData.append( ',' );
			nodeData.append( String.valueOf(nodes.get(i).strain) );
			nodeData.append( ',' );
			nodeData.append( String.valueOf(nodes.get(i).dens) );
			nodeData.append( ',' );
			nodeData.append( String.valueOf(nodes.get(i).xvel) );
			nodeData.append( '\n' );


		}//end for

		// Then leave for later

	}//end Node



	// Write material point data at a given time
	public static void MaterialPoint(
		FileWriter mpData,
		List<MaterialPoint> mps,
		double t
	) throws IOException{


		// Write material point data
		for ( int i = 0; i < Constants.num_particles; ++i ) {


			mpData.append( String.valueOf(t) );
			mpData.append(',');
			mpData.append( String.valueOf(mps.get(i).xpos) );
			mpData.append( ',' );
			mpData.append( String.valueOf(mps.get(i).mass) );
			mpData.append( ',' );
			mpData.append( String.valueOf(mps.get(i).stress) );
			mpData.append( ',' );
			mpData.append( String.valueOf(mps.get(i).strain) );
			mpData.append( ',' );
			mpData.append( String.valueOf(mps.get(i).xvel) );
			mpData.append( '\n' );


		}//end for


	}// end MaterialPoint



}//end DataWrite
