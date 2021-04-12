/*
	Contains:
		class: MaterialPoint
			(Describes a material point of the system)

			time
			length
			mass
			xpos
			pnt_xvel
			xvel
			stress
			strain
			ymodulus
			left_nn
			right_nn
*/


// Defines the 'MaterialPoint' class
// which tells us about a material point, which is a little bit of continua
// between nodes
//
// Much in similarlity with the Node Class
public class MaterialPoint {

	
	// Standard attributes
	public double time;
	public double length;
	public double mass; // Mass of the material point


	// Position and physical velocity
	public double xpos;
	public double pnt_xvel; // Not the same as xvel! Physical velocity of particle


	// Measurable material properties
	public double xvel; // Contributes to node velocity measurement

	public double stress;
	public double strain;


	// Other continua properties
	public double ymodulus;


	// Nearest neighbor nodes
	public int left_nn;
	public int right_nn;


}//end MaterialPoint
