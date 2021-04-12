import java.util.*;


// Contains the constants that we use when running the system
public class Constants {
	

	// Number of nodes in the system
	public static final int num_nodes = 201;

	// Starting and final x points of the system
	public static final double xlowbnd = 0.;
	public static final double xhighbnd = 1.;

	// Size of a cell
	public static final double dx = ( xhighbnd - xlowbnd )/(num_nodes - 1);




	// Properties of particles
	public static final int num_mps = 4; // Number of particles per cell
	public static final int num_particles = (num_nodes - 1) * num_mps; // Number of total particles

	public static final boolean move_particles = false; // Move our particles in time?




	// Time properties
	public static final double tmax = 10.; // Max time to solve for
	public static final int tsteps = 1000000; // Number of steps to take

	public static final double dt = tmax/tsteps; // Time step size




	// Data writing properties
	public static final String node_csv_name = "test.csv";
	public static final String mp_csv_name = "mp_test.csv";

	public static final int record_frequency = 5000; // Number of timesteps between recording data

	// Make a big debug file?
	//	Note, writing the final creates a huge impact on performance
	public static final boolean debug_file = false;



	// Data csv Labels
	// See documentation on how to change
	public static final List<String> node_titles = Arrays.asList(
		"time",
		"xpos",
		"stress",
		"strain",
		"dens",
		"xvel"	
	);


	public static final List<String> mp_titles = Arrays.asList(
		"time",
		"xpos",
		"mass",
		"stress",
		"strain",
		"xvel"
	);




}//end Constants
