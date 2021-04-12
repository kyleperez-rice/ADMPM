import java.io.*; // Gets us CSV output
import java.util.*; // Gets us lists, array list, Collections, etc

/*
	Contains:
		class: debug
			(Functions that reveal information useful for debugging)

			class: NodeOutput
				(Functions that output node variables of a certain type)

				function: l_xvel
					(outputs l_xvel for a given set of nodes)
				function: dens
				function: xvel
				function: stress
				function: strain
				function: ymodulus
				function: mass

			class: MPOutput
				(Functions that output mp variables of a certain type)

				function: xpos
					(material point x position)
				function: pnt_xvel
				function: xvel
				function: stress
				function: strain
				function: ymodulus
				function: mass
				
			function: DataDump
				(Writes out a ton of data about material point/node)
				(positiion, for all points in time, and all the relevant)
				(quantities)
				(WARNING: produces an extremely long file!)
*/

// General debugging functions
public class Debug {
	



	// Subclass that lets you pick a particular class member
	// of the node class to pick to output the data of
	public static class NodeOutput {



		// Output the density of a List of nodes
		public static void dens( List<Node> nodes) {


			for (int i = 0; i < Constants.num_nodes; ++i) {


				System.out.println( "i = " + String.valueOf(i) + " Node Dens: " + String.valueOf(nodes.get(i).dens) );


			}//end for


		}//end dens


		// Output the xvel of a list of nodes
		public static void xvel( List<Node> nodes) {


			for (int i = 0; i < Constants.num_nodes; ++i) {


				System.out.println( "i = " + String.valueOf(i) + " Node Xvel: " + String.valueOf(nodes.get(i).xvel) );


			}//end for


		}//end xvel


		// Output the stress of a list of nodes
		public static void stress( List<Node> nodes) {


			for (int i = 0; i < Constants.num_nodes; ++i) {


				System.out.println( "i = " + String.valueOf(i) + " Node Stress: " + String.valueOf(nodes.get(i).stress) );


			}//end for


		}//end stress


		// Output the strain of a list of nodes
		public static void pres( List<Node> nodes) {


			for (int i = 0; i < Constants.num_nodes; ++i) {


				System.out.println( "i = " + String.valueOf(i) + " Node Pressure: " + String.valueOf(nodes.get(i).pres) );


			}//end for


		}//end strain


		// Output the strain of a list of nodes
		public static void gamma( List<Node> nodes) {


			for (int i = 0; i < Constants.num_nodes; ++i) {


				System.out.println( "i = " + String.valueOf(i) + " Node gamma: " + String.valueOf(nodes.get(i).gamma) );


			}//end for


		}//end ymodulus


		// Output the strain of a list of nodes
		public static void mass( List<Node> nodes) {


			for (int i = 0; i < Constants.num_nodes; ++i) {


				System.out.println( "i = " + String.valueOf(i) + " Node Mass: " + String.valueOf(nodes.get(i).mass) );


			}//end for


		}//end mass



	}//end NodeOutput




//------------------------------------------------------------------------------




	// Subclass that lets you pick a particular class member
	// of the MaterialPoint class to pick to output the data of
	//	mps = material points
	public static class MPOutput {



		// Output the xpos of a list of Material Points
		public static void xpos( List<MaterialPoint> mps) {


			for (int i = 0; i < Constants.num_particles; ++i) {


				System.out.println( "i = " + String.valueOf(i) + " Material Point Xpos: " + String.valueOf(mps.get(i).xpos) );


			}//end for


		}//end xpos


		// Output the physical velocity of a list of Material Points
		public static void pnt_xvel( List<MaterialPoint> mps) {


			for (int i = 0; i < Constants.num_particles; ++i) {


				System.out.println( "i = " + String.valueOf(i) + " Material Point Physical Velocity: " + String.valueOf(mps.get(i).pnt_xvel) );


			}//end for


		}//end pnt_xvel


		// Output the velocity of a list of Material Points
		public static void xvel( List<MaterialPoint> mps) {


			for (int i = 0; i < Constants.num_particles; ++i) {


				System.out.println( "i = " + String.valueOf(i) + " Material Point Velocity: " + String.valueOf(mps.get(i).xvel) );


			}//end for


		}//end xvel


		// Output the stress of a list of Material Points
		public static void stress( List<MaterialPoint> mps) {


			for (int i = 0; i < Constants.num_particles; ++i) {


				System.out.println( "i = " + String.valueOf(i) + " Material Point Stress: " + String.valueOf(mps.get(i).stress) );


			}//end for


		}//end stress


		// Output the strain of a list of Material Points
		public static void pres( List<MaterialPoint> mps) {


			for (int i = 0; i < Constants.num_particles; ++i) {


				System.out.println( "i = " + String.valueOf(i) + " Material Point Pressure: " + String.valueOf(mps.get(i).pres) );


			}//end for


		}//end strain


		// Output the ymodulus of a list of Material Points
		public static void gamma( List<MaterialPoint> mps) {


			for (int i = 0; i < Constants.num_particles; ++i) {


				System.out.println( "i = " + String.valueOf(i) + " Material Point Gamma: " + String.valueOf(mps.get(i).gamma) );


			}//end for


		}//end ymodulus


		// Output the mass of a list of Material Points
		//	Note: the mass of material points don't change over time!
		//	Should only have to run once!
		public static void mass( List<MaterialPoint> mps) {


			for (int i = 0; i < Constants.num_particles; ++i) {


				System.out.println( "i = " + String.valueOf(i) + " Material Point Mass: " + String.valueOf(mps.get(i).mass) );


			}//end for


		}//end mass



	}//end NodeOutput




//-------------------------------------------------------------------------------------



	// README: The following functions
	// output a very verbose, formatted data text file that tells us everything about
	// the nodes at a given time and the material points in their reach


	// Just make the header
	public static void DataDumpHeader(
		FileWriter debugData
	) throws IOException {


		debugData.append( "This file contains pratically all necessary info to debug your problem.\n" );
		debugData.append( "For each time, it gives a list of all of the nodes, and associated values\n" );
		debugData.append( "of each node, alongside the material points contained within a node's shape\n" );
		debugData.append( "function, and all of the material points' values.\n" );
		debugData.append( "It will also denote if any material points go untouched by a node.\n" );
		debugData.append( "\n\n\n" );


	}//end DataDumpHeader




	// Writes our node + mp data to our file in a nice format
	public static void DataDump(
		FileWriter debugData,
		List<Node> nodeData,
		List<MaterialPoint> mpData,
		double t
	) throws IOException {


		// Record the time
		debugData.append( "Time: " + String.valueOf( t ) + '\n' );


		// Then create a vector of boolean values that tells us whether or not a
		// material point is found in a given node
		List<Boolean> mp_used = new ArrayList<Boolean>( Arrays.asList( new Boolean[Constants.num_particles] ) );
		Collections.fill(mp_used, false); // Fill all the entries to be false

		// For a given element of time, print the data relevant to a given node
		for (int i = 0; i < Constants.num_nodes; ++i) {


			debugData.append('\t' + "Node " + String.valueOf(i) + '\n');
			// Then, we store the indices of the material points in the given node here:
			List<Integer> mp_indices = new ArrayList<Integer>();


			// Go through all of the material point data
			for (int j = 0; j < Constants.num_particles; ++j) {


				// If a material point lays in a given node's 'span', add it
				// to the list and note that it's been found
				//
				// A node's span (for linear shape functions) is just the node's x position
				// plus or minus the node's length.
				if (
					( mpData.get(j).xpos > (nodeData.get(i).xpos - nodeData.get(i).length) )
					&& 
					( mpData.get(j).xpos < (nodeData.get(i).xpos + nodeData.get(i).length) )
				) {

					mp_indices.add(j);
					mp_used.set(j, true);

				}//end if


			}//end for


			int size_mp_indices = mp_indices.size();


			// Print all of the material points contained in our node
			debugData.append( "\t\t" + "Material Points Contained in Node " + String.valueOf(i) + ":\n" );
			for (int j = 0; j < size_mp_indices; ++j) {


				debugData.append( "\t\t\t" + "MP " + String.valueOf(mp_indices.get(j)) + '\n');


			}//end for


			// Write the x position of the node and its associated mps
			debugData.append( "\t\t" + "Node xpos: " + String.valueOf(nodeData.get(i).xpos) + '\n' );
			for (int j = 0; j < size_mp_indices; ++j) {


				debugData.append( "\t\t\t" + "MP " + String.valueOf(mp_indices.get(j)) + " xpos: " + String.valueOf(mpData.get(mp_indices.get(j)).xpos) + '\n');


			}//end for


			// Write the density and mass of the node and its associated mps
			debugData.append( "\t\t" + "Node Density: " + String.valueOf(nodeData.get(i).dens) + '\n' );
			debugData.append( "\t\t" + "Node Mass: " + String.valueOf(nodeData.get(i).mass) + '\n' );
			for (int j = 0; j < size_mp_indices; ++j) {


				debugData.append( "\t\t\t" + "MP " + String.valueOf(mp_indices.get(j)) + " mass: " + String.valueOf(mpData.get(mp_indices.get(j)).mass) + '\n');


			}//end for


			// Write the xvelocity of the node and its associated mps
			debugData.append( "\t\t" + "Node xvel: " + String.valueOf(nodeData.get(i).xvel) + '\n' );
			for (int j = 0; j < size_mp_indices; ++j) {


				debugData.append( "\t\t\t" + "MP " + String.valueOf(mp_indices.get(j)) + " xvel: " + String.valueOf(mpData.get(mp_indices.get(j)).xvel) + '\n');
				debugData.append( "\t\t\t" + "MP " + String.valueOf(mp_indices.get(j)) + " pnt_xvel: " + String.valueOf(mpData.get(mp_indices.get(j)).pnt_xvel) + '\n');


			}//end for


			// Write the Stress/Strain/ymod of the node and its associated mps
			debugData.append( "\t\t" + "Node Stress: " + String.valueOf(nodeData.get(i).stress) + '\n' );
			debugData.append( "\t\t" + "Node Pressure: " + String.valueOf(nodeData.get(i).pres) + '\n' );
			debugData.append( "\t\t" + "Node gamma: " + String.valueOf(nodeData.get(i).gamma) + '\n' );
			for (int j = 0; j < size_mp_indices; ++j) {


				debugData.append( "\t\t\t" + "MP " + String.valueOf(mp_indices.get(j)) + " Stress: " + String.valueOf(mpData.get(mp_indices.get(j)).stress) + '\n');
				debugData.append( "\t\t\t" + "MP " + String.valueOf(mp_indices.get(j)) + " Pressure: " + String.valueOf(mpData.get(mp_indices.get(j)).pres) + '\n');
				debugData.append( "\t\t\t" + "MP " + String.valueOf(mp_indices.get(j)) + " Gamma: " + String.valueOf(mpData.get(mp_indices.get(j)).gamma) + '\n');
					

			}//end for
				


		}//end for


		// Print the unused material points
		debugData.append( '\t' + "Unused Material Points:" + '\n' );
		for (int j = 0; j < mp_used.size(); ++j) {


			if ( mp_used.get(j) == false ) {

				debugData.append( "\t\t" + "MP " + String.valueOf(j) + '\n' );
				debugData.append( "\t\t\t" + "xpos: " + String.valueOf(mpData.get(j).xpos) + '\n' );
				debugData.append( "\t\t\t" + "mass: " + String.valueOf(mpData.get(j).mass) + '\n' );
				debugData.append( "\t\t\t" + "xvel: " + String.valueOf(mpData.get(j).xvel) + '\n' );
				debugData.append( "\t\t\t" + "pnt_xvel: " + String.valueOf(mpData.get(j).pnt_xvel) + '\n' );
				debugData.append( "\t\t\t" + "stress: " + String.valueOf(mpData.get(j).stress) + '\n' );
				debugData.append( "\t\t\t" + "pressure: " + String.valueOf(mpData.get(j).pres) + '\n' );
				debugData.append( "\t\t\t" + "gamma: " + String.valueOf(mpData.get(j).gamma) + '\n' );

			}//end if


		}//end for


	}//end DataDump



}//end Debug
