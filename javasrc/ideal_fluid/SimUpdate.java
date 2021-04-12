import java.util.*; // Get lists, etc

// Class that contains functions that allow us to advance
// node/material point quantities in time
public class SimUpdate {
	

	// Compute the mass of a node, given the material point masses/posistions
	public static void ComputeNodeMasses(
		List<Node> nodes,
		List<MaterialPoint> mp_points
	) {


		// First set masses to 0
		for ( int i = 0; i < Constants.num_nodes; ++i ) {


			nodes.get(i).mass = 0.;


		}//end for


		// Then sum with respect to material point shape function
		// Do sum over the material points, but use the mp's
		// nearest nodes to cover all of the nodes
		//
		// This works only for linear shape functions!
		for ( int i = 0; i < Constants.num_particles; ++i ) {


			int left_nn = mp_points.get(i).left_nn;
			int right_nn = mp_points.get(i).right_nn;

			nodes.get( left_nn ).mass += mp_points.get(i).mass * nodes.get( left_nn ).shapef( mp_points.get(i).xpos );
			nodes.get( right_nn ).mass += mp_points.get(i).mass * nodes.get( right_nn ).shapef( mp_points.get(i).xpos );


		}//end for


	}//end ComputeNodeMasses




	// Move the particles
	public static void MoveParticles(
		List<MaterialPoint> mps
	) {


		// xposition = xposition + vel*dt
		for ( int i = 0; i < Constants.num_particles; ++i ) {


			mps.get(i).xpos += mps.get(i).pnt_xvel * Constants.dt;


		}//end for


	}//end MoveParticles



//------------------------------------------------------------------------------------



	// Updates the velocity, etc of the system
	//
	// What we are going to calculate:
	//	v_p(t+dt) = v_p(t) + Sum_{l: nodes} (v^L_l - v_l)shapef_l(mp xpos)
	//	pnt_xvel = sum_l v^L shapef(mp xpos)
	// v_l(t+dt) = 1/m_l * sum_p m_p * v_p(t+dt) * shapef(xpos) 
	public static void UpdateVelocity(
		List<Node> nodes,
		List<MaterialPoint> mps,
		double t
	) {


		// First compute v_p = v_p - sum_{l: nodes}v_l shapef_l()
		for ( int i = 0; i < Constants.num_particles; ++i ) {


			int left_nn = mps.get(i).left_nn;
			int right_nn = mps.get(i).right_nn;

			mps.get(i).xvel -= nodes.get( left_nn ).xvel * nodes.get( left_nn ).shapef( mps.get(i).xpos );
			mps.get(i).xvel -= nodes.get( right_nn ).xvel * nodes.get( right_nn ).shapef( mps.get(i).xpos );


		}//end for


		// Then compute v^L = v + dv/dt * dt
		// = v^n + f*dt - dt/m * sum_{p: mps} v_p * sigma_p * d_shapef(mpxpos)
		//
		// First: the external forces
		for ( int i = 0; i < Constants.num_nodes; ++i ) {


			nodes.get(i).xvel += Accelerations.external_x( nodes.get(i), t )*Constants.dt;


		}//end for


		// Then include the material point contributions
		// only using the closest nodes to each point
		for ( int i = 0; i < Constants.num_particles; ++i ) {


			int left_nn = mps.get(i).left_nn;
			int right_nn = mps.get(i).right_nn;

			nodes.get( left_nn ).xvel -= mps.get(i).length * mps.get(i).stress * nodes.get( left_nn ).d_shapef( mps.get(i).xpos ) * Constants.dt / nodes.get( left_nn ).mass;
			nodes.get( right_nn ).xvel -= mps.get(i).length * mps.get(i).stress * nodes.get( right_nn ).d_shapef( mps.get(i).xpos ) * Constants.dt / nodes.get( right_nn ).mass;


		}//end for


		// Then enforce the boundary conditions!
		nodes.get(0).xvel = BoundaryConditions.Velocity.Left(t);
		nodes.get( Constants.num_nodes - 1 ).xvel = BoundaryConditions.Velocity.Right(t);


		// Next, finish computing v_p(t+dt)
		// Just need to do v_p = v_p + sum_l v_l * shapef()
		// By pnt_xvel = sum_l v^L * shapef()
		// v_p = v_p + pnt_xvel
		for ( int i = 0; i < Constants.num_particles; ++i ) {


			int left_nn = mps.get(i).left_nn;
			int right_nn = mps.get(i).right_nn;

			mps.get(i).pnt_xvel = nodes.get( left_nn ).xvel * nodes.get( left_nn ).shapef( mps.get(i).xpos );
			mps.get(i).pnt_xvel += nodes.get( right_nn ).xvel * nodes.get( right_nn ).shapef( mps.get(i).xpos );

			mps.get(i).xvel += mps.get(i).pnt_xvel;


		}//end for



		// Then move the particles and recompute node masses
		if ( Constants.move_particles) {

			SimUpdate.MoveParticles(mps);
			SimUpdate.ComputeNodeMasses(nodes, mps);

		}//end if
		


		// The compute the nodal velocity at the future time
		// Do this by setting nodal velocity to be zero, then
		// running sum over material points and dividing by mass
		// And fixing boundaries one more time
		for ( int i = 0; i < Constants.num_nodes; ++i ) {


			nodes.get(i).xvel = 0.;


		}//end for


		for ( int i = 0; i < Constants.num_particles; ++i ) {


			int left_nn = mps.get(i).left_nn;
			int right_nn = mps.get(i).right_nn;

			nodes.get( left_nn ).xvel += mps.get(i).mass * mps.get(i).xvel * nodes.get( left_nn ).shapef( mps.get(i).xpos );
			nodes.get( right_nn ).xvel += mps.get(i).mass * mps.get(i).xvel *  nodes.get( right_nn ).shapef( mps.get(i).xpos );


		}//end for


		// Then divide by mass
		for ( int i = 0; i < Constants.num_nodes; ++i ) {


			nodes.get(i).xvel /= nodes.get(i).mass;


		}//end for


		// Enforce boundary conditions one more time!
		nodes.get(0).xvel = BoundaryConditions.Velocity.Left(t);
		nodes.get( Constants.num_nodes - 1 ).xvel = BoundaryConditions.Velocity.Right(t);


	}//end UpdateVelocity


	




//---------------------------------------------------------------------------------



	// Class that lets us update certain particle quantities
	//
	// For velocity:
	//	v_p(t + dt) = v_p(t) + Sum_{l: all nodes} (v_l^L - v_l)*shapef_l(x_p)
	//	For every material point p
	//
	// For strain:
	//	strain_p(t+dt) = strain_p(t) + Sum_{l: all nodes}(v_l * d_shapef_l(x_p))
	//
	// NOTE:
	//	This is written in the order in which you should call this in your program!
	public static class UpdateParticle {


		// Update the particle pressure
		public static void Pressure (
			List<MaterialPoint> mps,
			List<Node> nodes
		) {

			// Strain rate is just sum_{nodes l} v_l d_shapef_l (xpos)
			// For linear shape functions, becomes two terms
			for ( int i = 0; i < Constants.num_particles; ++i ) {


				int left_nn = mps.get(i).left_nn;
				int right_nn = mps.get(i).right_nn;

				mps.get(i).pres += (mps.get(i).gamma - 1) * mps.get(i).xvel * nodes.get( left_nn ).pres * nodes.get( left_nn ).d_shapef( mps.get(i).xpos ) * Constants.dt;
				mps.get(i).pres -= mps.get(i).gamma * nodes.get( left_nn ).pres * nodes.get( left_nn ).xvel * nodes.get( left_nn ).d_shapef( mps.get(i).xpos ) * Constants.dt;
				mps.get(i).pres += (mps.get(i).gamma - 1) * mps.get(i).xvel * nodes.get( right_nn ).pres * nodes.get( right_nn ).d_shapef( mps.get(i).xpos ) * Constants.dt;
				mps.get(i).pres -= mps.get(i).gamma * nodes.get( right_nn ).pres * nodes.get( right_nn ).xvel * nodes.get( right_nn ).d_shapef( mps.get(i).xpos ) * Constants.dt;


			}//end for


		}//end Strain



		// Update the particle stress
		public static void Stress (
			List<MaterialPoint> mps,
			List<Node> nodes
		) {


			for ( int i = 0; i < Constants.num_particles; ++i ) {


				// Not very general; relies explicitly on stress-strain relation!
				mps.get(i).stress = -mps.get(i).pres;


			}//end for


		}//end Stress



	}//end UpdateParticle



//-----------------------------------------------------------------------------------



	// Uses material point quantities to calculate node quantities
	// Does a sum of the form:
	// q_kl = 1/m_kl * Sum_{p: all mps} m_kp * q_kp * shapef_l(x_p)
	// for every l in the mesh, given a particular type
	public static class UpdateNode {


		public static void Density(
			List<Node> nodes,
			List<MaterialPoint> mps
		) {

			
			// Node density = nodemass/length
			for ( int i = 0; i < Constants.num_nodes; ++i ) {


				nodes.get(i).dens = nodes.get(i).mass / nodes.get(i).length;


			}//end for


			// Boundary nodes are of half-length
			// so need to multiply by 2!
			nodes.get(0).dens = 2. * nodes.get(0).dens;
			nodes.get( nodes.size()-1 ).dens = 2. * nodes.get( nodes.size()-1 ).dens;


		}//end Density




		public static void Pressure(
			List<Node> nodes,
			List<MaterialPoint> mps
		) {


			// Set node strain to be 0
			for ( int i = 0; i < Constants.num_nodes; ++i ) {


				nodes.get(i).pres = 0.;


			}//end for


			// Then over use the material points' nearest nodes
			// to construct the node velocity
			for ( int i = 0; i < Constants.num_particles; ++i ) {


				int left_nn = mps.get(i).left_nn;
				int right_nn = mps.get(i).right_nn;

				nodes.get( left_nn ).pres += mps.get(i).mass * mps.get(i).pres * nodes.get( left_nn ).shapef( mps.get(i).xpos );
				nodes.get( right_nn ).pres += mps.get(i).mass * mps.get(i).pres * nodes.get( right_nn ).shapef( mps.get(i).xpos );


			}//end for


			// Then divide by the mass for every node
			for ( int i = 0; i < Constants.num_nodes; ++i ) {


				nodes.get(i).pres /= nodes.get(i).mass;


			}//end for


		}//end Strain




		// Don't actually need to call this one
		public static void Stress(
			List<Node> nodes,
			List<MaterialPoint> mp_points
		) {


			for ( int i = 0; i < Constants.num_nodes; ++i ) {


				// In case you have different stress-strain relation,
				//nodes.get(i).stress = MPMMath.MPWeightSum.Stress(nodes.get(i), mp_points);
				nodes.get(i).stress = -nodes.get(i).pres;


			}//end for


		}//end Stress




	}//end UpdateNode



}//end SimUpdate
