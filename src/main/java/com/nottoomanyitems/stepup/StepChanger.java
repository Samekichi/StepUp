package com.nottoomanyitems.stepup;

import com.nottoomanyitems.stepup.ConfigHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings.Options;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public final class StepChanger {
    public static EntityPlayer player;
    public static KeyBinding myKey;
    public static int autoJumpState = -1; //0 StepUp, 1 None, 2 Minecraft
    public static Boolean firstRun = true;
    private String mc_version;
    
    private Minecraft mc = Minecraft.func_71410_x();

    public static void createKey() {
        myKey = new KeyBinding("Toggle StepUp", 36, "StepUp");
        ClientRegistry.registerKeyBinding((KeyBinding)myKey);
    }

    @SubscribeEvent
    public void tickEvent(TickEvent.PlayerTickEvent event) {
    	boolean b = mc.field_71474_y.func_74308_b(Options.AUTO_JUMP);
        player = event.player;
        String m = "";
        if(player.func_70093_af()) {
        	StepChanger.player.field_70138_W = .6f;
        }else if(autoJumpState == 0){
        	StepChanger.player.field_70138_W = 1.25f;
        	m = (Object)TextFormatting.YELLOW + "StepUp" + (Object)TextFormatting.WHITE + " Auto Jump " + (Object)TextFormatting.GREEN + "Enabled";
        	if(b == true){
            	mc.field_71474_y.func_74306_a(Options.AUTO_JUMP, 0);
            }
        }else if(autoJumpState == 1){
        	StepChanger.player.field_70138_W = .6f;
        	m = (Object)TextFormatting.RED + "No Auto Jump Enabled";
        	if(b == true){
            	mc.field_71474_y.func_74306_a(Options.AUTO_JUMP, 0);
            }
        }else if(autoJumpState == 2){
        	StepChanger.player.field_70138_W = .6f;
        	m = (Object)TextFormatting.YELLOW + "Minecraft" + (Object)TextFormatting.WHITE + " Auto Jump " + (Object)TextFormatting.GREEN + "Enabled";
        	if(b == false){
            	mc.field_71474_y.func_74306_a(Options.AUTO_JUMP, 0);
            }
        }
        
        if (firstRun && autoJumpState != -1) {
        	mc_version = Minecraft.func_71410_x().func_175600_c();
        	if(!Main.versionChecker.isLatestVersion()) {
        		String m2 = "";
        		ClickEvent versionCheckChatClickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, "https://nottoomanyitems.wixsite.com/mods/step-up");
        		m2 = (Object)TextFormatting.GOLD + "Update Available: " + (Object)TextFormatting.DARK_AQUA + "[" + (Object)TextFormatting.YELLOW + "StepUp" + (Object)TextFormatting.WHITE + " v" + VersionChecker.getLatestVersion() + (Object)TextFormatting.DARK_AQUA + "]";
        		TextComponentString component = new TextComponentString(m2);
        		Style s = component.func_150256_b();
        		s.func_150241_a(versionCheckChatClickEvent);
        		component.func_150255_a(s);
        		player.func_145747_a((ITextComponent) component);
        	}
        	if(mc_version.contains("1.12")){
        		player.func_146105_b(new TextComponentString(m), true);
        	}else {
        		player.func_145747_a((ITextComponent)new TextComponentString(m));
        	}
            firstRun = false;
        }
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
    	String m = "";
    	boolean b = mc.field_71474_y.func_74308_b(Options.AUTO_JUMP);
    	
        if (myKey.func_151468_f()) {
            
            if(autoJumpState == 0){
            	StepChanger.player.field_70138_W = .6f;
            	m = (Object)TextFormatting.RED + "No Auto Jump Enabled";
            	if(b == true){
                	mc.field_71474_y.func_74306_a(Options.AUTO_JUMP, 0);
                }
            	autoJumpState = 1;
            }else if(autoJumpState == 1){
            	StepChanger.player.field_70138_W = .6f;
            	m = (Object)TextFormatting.YELLOW + "Minecraft" + (Object)TextFormatting.WHITE + " Auto Jump " + (Object)TextFormatting.GREEN + "Enabled";
            	if(b == false){
                	mc.field_71474_y.func_74306_a(Options.AUTO_JUMP, 0);
                }
            	autoJumpState = 2;
            }else if(autoJumpState == 2){
            	StepChanger.player.field_70138_W = 1.25f;
            	m = (Object)TextFormatting.YELLOW + "StepUp" + (Object)TextFormatting.WHITE + " Auto Jump " + (Object)TextFormatting.GREEN + "Enabled";
            	if(b == true){
                	mc.field_71474_y.func_74306_a(Options.AUTO_JUMP, 0);
                }
            	autoJumpState = 0;
            }
            
            if(mc_version.contains("1.12")){
        		player.func_146105_b(new TextComponentString(m), true);
        	}else {
        		player.func_145747_a((ITextComponent)new TextComponentString(m));
        	}
            ConfigHandler.changeConfig();
        }
    }
}

