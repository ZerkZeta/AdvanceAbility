package com.ZerkZeta.AdvanceAbility.Util;

public class ParticleEffectProperties {
	
	private float OffsetX;
	private float OffsetY;
	private float OffsetZ;
	private float Speed;
	private int Amount;
	
	public ParticleEffectProperties(String Offsetx,String Offsety,String Offsetz,String speed, String amount)throws Exception{
		this.OffsetX = Float.parseFloat(Offsetx);
		this.OffsetY = Float.parseFloat(Offsety);
		this.OffsetZ = Float.parseFloat(Offsetz);
		this.Speed = Float.parseFloat(speed);
		this.Amount = Integer.parseInt(amount);
	}
	
	public ParticleEffectProperties(float Offsetx,float Offsety,float Offsetz,float speed, int amount){
		this.OffsetX = Offsetx;
		this.OffsetY = Offsety;
		this.OffsetZ = Offsetz;
		this.Speed = speed;
		this.Amount = amount;
	}
	
	public float getOffsetX(){
		return this.OffsetX;
	}
	
	public float getOffsetY(){
		return this.OffsetY;
	}
	
	public float getOffsetZ(){
		return this.OffsetZ;
	}
	
	public float getSpeed(){
		return this.Speed;
	}

	public int getAmount(){
		return this.Amount;
	}
	
}
