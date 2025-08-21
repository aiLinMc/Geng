package net.mcreator.geng.procedures;

import org.checkerframework.checker.units.qual.s;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.AdvancementHolder;

import net.mcreator.geng.network.GengModVariables;
import net.mcreator.geng.init.GengModMenus;

public class PolarBearLocationCiGUICloseProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
			_vars.polar_bear_x = new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert((entity instanceof Player _entity && _entity.containerMenu instanceof GengModMenus.MenuAccessor _menu0) ? _menu0.getMenuState(0, "location_x", "") : "");
			_vars.syncPlayerVariables(entity);
		}
		{
			GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
			_vars.polar_bear_y = new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert((entity instanceof Player _entity && _entity.containerMenu instanceof GengModMenus.MenuAccessor _menu1) ? _menu1.getMenuState(0, "location_y", "") : "");
			_vars.syncPlayerVariables(entity);
		}
		{
			GengModVariables.PlayerVariables _vars = entity.getData(GengModVariables.PLAYER_VARIABLES);
			_vars.polar_bear_z = new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert((entity instanceof Player _entity && _entity.containerMenu instanceof GengModMenus.MenuAccessor _menu2) ? _menu2.getMenuState(0, "location_z", "") : "");
			_vars.syncPlayerVariables(entity);
		}
		if (entity instanceof Player _player)
			_player.closeContainer();
		if (entity instanceof ServerPlayer _player) {
			AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("geng:ride_polar_bear"));
			if (_adv != null) {
				AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
				if (!_ap.isDone()) {
					for (String criteria : _ap.getRemainingCriteria())
						_player.getAdvancements().award(_adv, criteria);
				}
			}
		}
	}
}