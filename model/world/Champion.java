package model.world;

import java.awt.Point;
import java.util.ArrayList;


import model.abilities.Ability;
import model.effects.Effect;

@SuppressWarnings("rawtypes")
public abstract class Champion implements Damageable,Comparable {
	private String name;
	private int maxHP;
	private int currentHP;
	private int mana;
	private int maxActionPointsPerTurn;
	private int currentActionPoints;
	private int attackRange;
	private int attackDamage;
	private int speed;
	private ArrayList<Ability> abilities;
	private ArrayList<Effect> appliedEffects;
	private Condition condition;
	private Point location;
	

	public Champion(String name, int maxHP, int mana, int actions, int speed, int attackRange, int attackDamage) {
		this.name = name;
		this.maxHP = maxHP;
		this.mana = mana;
		this.currentHP = this.maxHP;
		this.maxActionPointsPerTurn = actions;
		this.speed = speed;
		this.attackRange = attackRange;
		this.attackDamage = attackDamage;
		this.condition = Condition.ACTIVE;
		this.abilities = new ArrayList<Ability>();
		this.appliedEffects = new ArrayList<Effect>();
		this.currentActionPoints=maxActionPointsPerTurn;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public String getName() {
		return name;
	}

	public void setCurrentHP(int hp) {

		if (hp <= 0) {
			currentHP = 0;
			condition=Condition.KNOCKEDOUT;
			
		} 
		else if (hp > maxHP)
			currentHP = maxHP;
		else
			currentHP = hp;

	}

	
	public int getCurrentHP() {

		return currentHP;
	}

	public ArrayList<Effect> getAppliedEffects() {
		return appliedEffects;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getAttackDamage() {
		return attackDamage;
	}

	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int currentSpeed) {
		if (currentSpeed < 0)
			this.speed = 0;
		else
			this.speed = currentSpeed;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point currentLocation) {
		this.location = currentLocation;
	}

	public int getAttackRange() {
		return attackRange;
	}

	public ArrayList<Ability> getAbilities() {
		return abilities;
	}

	public int getCurrentActionPoints() {
		return currentActionPoints;
	}

	public void setCurrentActionPoints(int currentActionPoints) {
		if(currentActionPoints>maxActionPointsPerTurn)
			currentActionPoints=maxActionPointsPerTurn;
		else 
			if(currentActionPoints<0)
			currentActionPoints=0;
		this.currentActionPoints = currentActionPoints;
	}

	public int getMaxActionPointsPerTurn() {
		return maxActionPointsPerTurn;
	}

	public void setMaxActionPointsPerTurn(int maxActionPointsPerTurn) {
		this.maxActionPointsPerTurn = maxActionPointsPerTurn;
	}

	public int compareTo(Object o)
	{
		Champion c = (Champion)o;
		if(speed==c.speed)
			return name.compareTo(c.name);
		return -1 * (speed-c.speed);
	}
	
public abstract void useLeaderAbility(ArrayList<Champion> targets);

@Override
public String toString() {
	return "Champion:"+"\n"+"name=" + name + "\n" + " maxHP=" + maxHP +"\n" + " mana=" + mana +"\n" + " maxActionPointsPerTurn="
			+ maxActionPointsPerTurn + "\n" + " attackRange=" + attackRange +"\n" + " attackDamage=" + attackDamage +"\n" + " speed="
			+ speed +"\n"+"\n" + " Ability 1: "+this.getAbilities().get(0).getName()+"\n"+this.getAbilities().get(0).abilityDetailsForChamp()+"\n"+"Ability 2: "+this.getAbilities().get(1).getName()+"\n"
			+this.getAbilities().get(1).abilityDetailsForChamp()+"\n"+"Ability 3: "+this.getAbilities().get(2).getName()+"\n"
			+this.getAbilities().get(2).abilityDetailsForChamp();
}
	public String champDetails(){
		String s="Name=" + name + "\n" + " MaxHP=" + maxHP + "\n" + " CurrentHP=" + currentHP +"\n" + "Current Action Points= " + currentActionPoints + "\n" +" mana=" + mana +"\n" + " maxActionPointsPerTurn="
				+ maxActionPointsPerTurn + "\n" + " attackRange=" + attackRange +"\n" + " AttackDamage=" + attackDamage +"\n" + " Speed="
				+ speed +"\n" +"Condition= "+condition+"\n"+"AppliedEffects="+"\n"+ appliedEffectsDetails();
		return s;
	}

	public String champListOfAbilities(){
	String res="list of abilities: [";
	for(int i=0;i<this.getAbilities().size();i++){
		res+=this.getAbilities().get(i).getName();
	}
	res+=" ]";
	return res;
	}

	public String appliedEffectsDetails() {
		String res = "";
		for (int i = 0; i < getAppliedEffects().size(); i++) {

			String effectDetails=getAppliedEffects().get(i).getName()+"  "+"Duration="+getAppliedEffects().get(i).getDuration()+"\n";
			res+=effectDetails;
		}
		return res;
	}

}
