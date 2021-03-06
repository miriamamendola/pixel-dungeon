/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.watabou.pixeldungeon.items.scrolls;

import com.watabou.noosa.audio.Sample;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.actors.buffs.Invisibility;
import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.effects.SpellSprite;
import com.watabou.pixeldungeon.effects.particles.EnergyParticle;
import com.watabou.pixeldungeon.utils.GLog;

public class ScrollOfRecharging extends Scroll {

	{
		name = "Pergamena della Ricarica";
	}
	
	@Override
	protected void doRead() {
		
		int count = curUser.belongings.charge( true );		
		charge( curUser );
		
		Sample.INSTANCE.play( Assets.SND_READ );
		Invisibility.dispel();
		
		if (count > 0) {
			GLog.i( "una sferzata di energia attraversa il tuo zaino, ricaricando " + (count > 1 ? "le tue bacchette." : "la tua bacchetta.") );
			SpellSprite.show( curUser, SpellSprite.CHARGE );
		} else {
			GLog.i( "una sferzata di energia attraversa il tuo zaino, ma non e' succeso nulla." );
		}
		setKnown();
		
		readAnimation();
	}
	
	@Override
	public String desc() {
		return
			"Il potere magico rinchiuso in questa pergamena, quando viene rilasciato, " +
			"ricarica completamente tutte le bacchette del lettore.";
	}
	
	public static void charge( Hero hero ) {
		hero.sprite.centerEmitter().burst( EnergyParticle.FACTORY, 15 );
	}
	
	@Override
	public int price() {
		return isKnown() ? 40 * quantity : super.price();
	}
}
