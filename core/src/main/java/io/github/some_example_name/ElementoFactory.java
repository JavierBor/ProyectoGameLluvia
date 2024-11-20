package io.github.some_example_name;

import com.badlogic.gdx.math.MathUtils;

public class ElementoFactory implements AbstractFactory{

	@Override
	public Elemento crearGotaBuena() {
		return new GotaBuena(MathUtils.random(0, 800-64), 480, 64, 64);
	}

	@Override
	public Elemento crearGotaMala() {
		return new GotaMala(MathUtils.random(0, 800-64), 480, 64, 64);
	}

	@Override
	public Elemento crearRayo() {
		return new Rayo(MathUtils.random(0, 800-64), 480, 64, 64);
	}

	@Override
	public Elemento crearSol() {
		return new Sol(MathUtils.random(0, 800-64), 480, 64, 64);
	}

	@Override
	public Elemento crearCopoNieve() {
		// TODO Auto-generated method stub
		return new CopoNieve(MathUtils.randomBoolean() ? 0 : 800 - 64, 480, 64, 64);
	}
}
