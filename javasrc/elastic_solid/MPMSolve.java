import java.io.*;
import java.util.*; // Get lists, etc


// Our main program
// Puts everything together
public class MPMSolve {



	public static void main( String args[] ) throws IOException {


		// Get start time
		final long startTime = System.currentTimeMillis();


//---------------------------------------------------------------
		// SETUP



		// Our time
		double t = 0.;




		// Our data files
		FileWriter nodeData = new FileWriter(Constants.node_csv_name);
		FileWriter mpData = new FileWriter(Constants.mp_csv_name);
		FileWriter debugData = new FileWriter("debug.txt");

		if ( !Constants.debug_file ) {

			debugData.append("No debug file produced.\n");
			debugData.flush();
			debugData.close();		

		}//end if


		


		// Give our data files some headers!
		DataWrite.MakeHeaders(nodeData, Constants.node_titles);
		DataWrite.MakeHeaders(mpData, Constants.mp_titles);

		if ( Constants.debug_file ) {

			Debug.DataDumpHeader(debugData);

		}//end if



//-----------------------------------------------------------------
		// Creating data arrays



		// Our current-time data
		List<Node> nodes = new ArrayList<Node>();
		List<MaterialPoint> mp_points = new ArrayList<MaterialPoint>();



		// Populate our lists with enough entries to make an initial state
		for ( int i = 0; i < Constants.num_nodes; ++i ) {


			nodes.add( new Node() );


		}//end for

		for ( int i = 0; i < Constants.num_particles; ++i ) {


			mp_points.add( new MaterialPoint() );


		}//end for




//-----------------------------------------------------------------
		// Initializing our system		




		// Then make the initial state
		Initializations.InitializeMesh(nodes);
		Initializations.InitializeMaterialPoints(mp_points, nodes);
		


		// Then get the nearest nodes to our material points
		// and find the initial node masses
		MPMMath.GetNearNodes(mp_points);
		SimUpdate.ComputeNodeMasses(nodes, mp_points);




//-----------------------------------------------------------------
		// Time evolution




		// Then evolve the system in time
		for ( int tt = 0; tt < Constants.tsteps; ++tt ) {



			// Data recording
			if (tt % Constants.record_frequency == 0) {

				// First save our data
				DataWrite.Node(nodeData, nodes, t);
				DataWrite.MaterialPoint(mpData, mp_points, t);

				if ( Constants.debug_file ) {

					Debug.DataDump(debugData, nodes, mp_points, t);

				}//end if

			}//end if


			// Then use these Lagrangian quantities to find the Eulerian ones
			// At the next timestep
			SimUpdate.UpdateParticle.Strain(mp_points, nodes);
			SimUpdate.UpdateVelocity(nodes, mp_points, t);
			SimUpdate.UpdateParticle.Stress(mp_points, nodes);



			// Then update the node quantities
			SimUpdate.UpdateNode.Density(nodes, mp_points);
			SimUpdate.UpdateNode.Strain(nodes, mp_points);
			SimUpdate.UpdateNode.Stress(nodes, mp_points);



			// Advance system in time
			t += Constants.dt;




		}//end for


		// Final save of the data
		DataWrite.Node(nodeData, nodes, t);
		DataWrite.MaterialPoint(mpData, mp_points, t);




		// Flush and close our files
		nodeData.flush();
		mpData.flush();

		nodeData.close();
		mpData.close();


		if ( Constants.debug_file )  {

			debugData.flush();
			debugData.close();

		}//end if
		

		// Get final time and print total time taken to run the program
		final long endTime = System.currentTimeMillis();

		System.out.println("Total execution time: " + (endTime - startTime));


	}//end main
	




}//end MPMSolve
