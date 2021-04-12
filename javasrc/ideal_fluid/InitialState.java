/*
	Contains:
		class: InitialState
			(Tells us about the initial state of the system)

			function: Density
				(t = 0 density)
			function: Strain
				(t = 0 strain)
			function: Xvelocity
				(t = 0 x velocity)
			function: Ymodulus
				(t = 0 young's modulus)
*/



// Gives the t=0 state of the system
public class InitialState {


	// t = 0 density
	public static double Density( double x ) {


		double rho = 1.;

		return rho;


	}//end Density



	// t = 0 strain
	public static double Pressure( double x ) {


		//double amplitude = 1.;

		return 1.;


	}//end Strain



	// t = 0 x velocity
	public static double XVelocity( double x ) {


		return 0.;


	}//end XVelocity




	// t = 0 Young's Modulus
	public static double gamma( double x ) {


		return 1.;


	}//end YModulus




	// t = 0 Stress
	/*
	public static double Stress( double x ) {


		return -this.Pressure(x);


	}//end Stress
	*/




}//end InitialState
