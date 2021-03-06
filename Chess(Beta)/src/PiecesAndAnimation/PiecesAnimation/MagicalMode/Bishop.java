/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PiecesAndAnimation.PiecesAnimation.MagicalMode;

import PiecesAndAnimation.PiecesAnimation.PieceAnimation;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;

/**
 *
 * @author shaks
 */
public class Bishop extends PieceAnimation
{
    public Bishop(SimpleApplication app, int i, int j, boolean good)
    {
        super(app);
        this.good = good;
        playerWalkDirection.set(i, 0, j);
        startPosition.set(playerWalkDirection);
        destination.set(i, 0, j);
        modelScale = 0.72f;
        attackIteration = 1;
        rangeAttack = true;
    }

    @Override
    protected void LoadModel()
    {   
        loadAnim();
        loadTexture();
        setChannelsAndControls();
        
        localNode.attachChild(standNode);
        
        attackNode.setLocalScale(modelScale);
        deathNode.setLocalScale(modelScale);
        standNode.setLocalScale(modelScale);
        walkNode.setLocalScale(modelScale);  
        
        
        headText.setText("Bishop");             // the text
        
    }
    
    @Override
    protected void loadAnim()
    {
       // System.out.println("Load Anim started");
        
        attackNode = (Node)assetManager.loadModel("Models/Animations/Magical/bishopAttack01/bishopAttack.j3o");
        
        deathNode = (Node)assetManager.loadModel("Models/Animations/Magical/bishopDeath01/bishopDeath.j3o");
        
        standNode = (Node)assetManager.loadModel("Models/Animations/Magical/bishopStand01/bishopStand.j3o");
        
        walkNode = (Node)assetManager.loadModel("Models/Animations/Magical/bishopWalk01/bishopWalk.j3o");
        //System.out.println("Load Anim Done");
    }
    
    @Override
    protected void loadTexture()
    {
        //System.out.println("Load Texture started");
        Texture texture = assetManager.loadTexture("Textures/Animations/Magical/bishop/bishop_diffuse.png");
        if(!good)
            texture = assetManager.loadTexture("Textures/Animations/Magical/bishop/bishop_diffuse(evil).png");
        texture.setWrap(Texture.WrapMode.Repeat);
        mat.setTexture("ColorMap", texture);
        
        attackNode.setMaterial(mat);
        deathNode.setMaterial(mat);
        standNode.setMaterial(mat);
        walkNode.setMaterial(mat);
        //System.out.println("Load Texture Done");
    }
    
    
    
    // G  ---- ++++ ))) 
    
    
    @Override
    protected void setChannelsAndControls()
    {
        //System.out.println("Load Channels started");
        attackAnimControl = attackNode.getChild("bishopAttack").getControl(AnimControl.class);
        attackCh = attackAnimControl.createChannel();
        attackCh.setAnim("Attack");
        attackCh.setLoopMode(LoopMode.DontLoop);
        attackAnimControl.addListener(this);
        
        deathAnimControl = deathNode.getChild("bishopDeath").getControl(AnimControl.class);
        deathCh = deathAnimControl.createChannel();
        deathCh.setAnim("Death");
        deathCh.setLoopMode(LoopMode.DontLoop);
        deathAnimControl.addListener(this);
        
        standAnimControl = standNode.getChild("bishopStand").getControl(AnimControl.class);
        standCh = standAnimControl.createChannel();
        standCh.setAnim("Stand");
        standCh.setLoopMode(LoopMode.Loop);
        standAnimControl.addListener(this);
        
        walkAnimControl = walkNode.getChild("bishopWalk").getControl(AnimControl.class);
        walkCh = walkAnimControl.createChannel();
        walkCh.setAnim("WalkDig");
        walkCh.setLoopMode(LoopMode.DontLoop);
        walkAnimControl.addListener(this);
        //System.out.println("Load Chanels Done");
    }
    
    @Override
    public boolean isEquale(Spatial selectedObject)
    {
        if(standNode == null)
            return false;
        return selectedObject == (Spatial)standNode.getChild("bishopStand");
    }
    
}
