/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PiecesAnimation;

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
public class King extends Piece
{
    
    private boolean good;
    public King(SimpleApplication app, int i, int j, boolean good)
    {
        super(app);
        this.good = good;
        playerWalkDirection.set(i, 0, j);
        startPosition.set(playerWalkDirection);
        if(good)
            startPosition.x -= 2.0f;
        else
            startPosition.x += 2.0f;
        modelScale = 1.0f;
        destination.set(i, 0, j);
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
        
        
        headText.setText("king");             // the text
        
    }
    
    @Override
    protected void loadAnim()
    {
        
        attackNode = (Node)assetManager.loadModel("Models/Animations/kingAttack01/kingAttack.j3o");
        
        deathNode = (Node)assetManager.loadModel("Models/Animations/kingDeath01/kingDeath.j3o");
        
        standNode = (Node)assetManager.loadModel("Models/Animations/kingStand01/kingStand.j3o");
        
        walkNode = (Node)assetManager.loadModel("Models/Animations/kingWalk01/kingWalk.j3o");
    }
    
    @Override
    protected void loadTexture()
    {
        Texture texture = assetManager.loadTexture("Textures/Animations/king/king_diffuse.png");
        if(!good)
            texture = assetManager.loadTexture("Textures/Animations/pawn/king_diffuse(evil).png");
        texture.setWrap(Texture.WrapMode.Repeat);
        mat.setTexture("ColorMap", texture);
        
        attackNode.setMaterial(mat);
        deathNode.setMaterial(mat);
        standNode.setMaterial(mat);
        walkNode.setMaterial(mat);
    }
    
    
    
    // G  ---- ++++ ))) 
    
    
    @Override
    protected void setChannelsAndControls()
    {
        attackAnimControl = attackNode.getChild("kingAttack").getControl(AnimControl.class);
        attackCh = attackAnimControl.createChannel();
        attackCh.setAnim("Attack");
        attackCh.setLoopMode(LoopMode.DontLoop);
        attackAnimControl.addListener(this);
        
        deathAnimControl = deathNode.getChild("kingDeath").getControl(AnimControl.class);
        deathCh = deathAnimControl.createChannel();
        deathCh.setAnim("Death");
        deathCh.setLoopMode(LoopMode.DontLoop);
        deathAnimControl.addListener(this);
        
        standAnimControl = standNode.getChild("kingStand").getControl(AnimControl.class);
        standCh = standAnimControl.createChannel();
        standCh.setAnim("Stand");
        standCh.setLoopMode(LoopMode.Loop);
        standAnimControl.addListener(this);
        
        walkAnimControl = walkNode.getChild("kingWalk").getControl(AnimControl.class);
        walkCh = walkAnimControl.createChannel();
        walkCh.setAnim("Walk");
        walkCh.setLoopMode(LoopMode.DontLoop);
        walkAnimControl.addListener(this);
    }
    
    @Override
    public boolean isEquale(Spatial selectedObject)
    {
        return selectedObject == (Spatial)standNode.getChild("kingStand");
    }
}
