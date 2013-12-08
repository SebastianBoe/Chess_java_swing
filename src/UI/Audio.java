package UI;

import Pieces.Bishop;
import Pieces.King;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Piece;
import Pieces.Queen;
import Pieces.Rook;

public class Audio
{
	private AudioPlaylist
	setup = 			new AudioPlaylist(),
	pawnUpgrade = 		new AudioPlaylist(),
	invalidMove = 		new AudioPlaylist(),
	mate = 				new AudioPlaylist(),
	chessmate = 		new AudioPlaylist(),
	attackSoundEffects= new AudioPlaylist(),
	firstBlood		  = new AudioPlaylist(),
	musicAction		  = new AudioPlaylist(),
	musicCalm =			new AudioPlaylist();
	
	private Album
	death = new Album(),
	move = new Album(),
	selected =  new Album();
	
	private long timeWhenActionStartedPlaying = 0;
	private boolean musicCalmIsPlaying = true;
	private final long lengthOfActionSong = 44*1000;
	
	private boolean muted = false;
	
	public Audio()
	{
		setup.add("GameFound");
		pawnUpgrade.add("KnightUpgradeComplete1");
		invalidMove.add("Error");
		mate.add("Warning");
		firstBlood.add("firstblood");
		musicCalm.add("destiny_small");
		musicAction.add("battletollan_small");
		chessmate.add("gameover2");
		addAttackSoundEffects();
		addDeathTracks();
		addMoveTracks();
		addSelectedTracks();
		setup();
	}
	
	public Audio(boolean muted)
	{
		setup.add("GameFound");
		pawnUpgrade.add("KnightUpgradeComplete1");
		invalidMove.add("Error");
		mate.add("Warning");
		firstBlood.add("firstblood");
		musicCalm.add("destiny_small");
		musicAction.add("battletollan_small");
		chessmate.add("gameover2");
		addAttackSoundEffects();
		addDeathTracks();
		addMoveTracks();
		addSelectedTracks();
		setup();
		if(muted)
		{
			this.muted = muted;
		}
	}
	
	public void stop()
	{
		setup.stop();
		pawnUpgrade.stop();
		invalidMove.stop();
		mate.stop();
		chessmate.stop();
		attackSoundEffects.stop();
		firstBlood.stop();
		musicAction.stop();
		musicCalm.stop();
		death.stop();
		move.stop();
		selected.stop();
	}
	
	public void updateMusic(boolean newMateInstance)
	{
		if(isMuted()) return;
		long time = System.currentTimeMillis();
		if(newMateInstance)
		{
			if(musicCalmIsPlaying)
			{
				musicCalm.stop();
				musicAction.playRandomTrack();
				musicCalmIsPlaying = false;
				timeWhenActionStartedPlaying = time;
			}
		}
		
		else if(!newMateInstance)
		{
			if(!musicCalmIsPlaying)
			{
				long timeSinceActionPlayed = time - timeWhenActionStartedPlaying;
				boolean ActionIsDonePlaying = timeSinceActionPlayed > lengthOfActionSong;
				if(ActionIsDonePlaying)
				{
					musicCalm.loopRandomTrack();
					musicCalmIsPlaying = true;
					musicAction.stop();
				}
			}
		}
	}

	public void selected(Object piece)
	{
		if(isMuted()) return;
		selected.playTrack((Piece)piece);
		updateMusic(false);
	}
	
	public void setup()
	{
		if(isMuted()) return;
		setup.playRandomTrack();
		musicCalm.loopRandomTrack();
	}
	
	public void musicCalm()
	{
		if(isMuted()) return;
		musicCalm.loopRandomTrack();
	}
	
	public void musicAction()
	{
		if(isMuted()) return;
		musicAction.playRandomTrack();
	}
	
	public void attack(Object piece)
	{
		if(isMuted()) return;
		attackSoundEffects.playRandomTrack();
		death.playTrack((Piece)piece);
		attackSoundEffects.playRandomTrack();
		updateMusic(false);
	}
	
	public void move(Object piece)
	{
		if(isMuted()) return;
		move.playTrack((Piece)piece);
		updateMusic(false);
	}
	
	public void invalidMove()
	{
		if(isMuted()) return;
		invalidMove.playRandomTrack();
		updateMusic(false);
	}
	
	public void firstBlood()
	{
		if(isMuted()) return;
		double random = Math.random();
		if(random < 0.25)
			firstBlood.playRandomTrack();
	}
	
	public void mate()
	{
		if(isMuted()) return;
		mate.playRandomTrack();
	}
	
	public void chessMate()
	{
		if(isMuted()) return;
		chessmate.playRandomTrack();
	}
	
	public void longDelay()
	{
		if(isMuted()) return;
		updateMusic(false);
	}
	
	public void PawnUpgrade()
	{
		if(isMuted()) return;
		pawnUpgrade.playRandomTrack();
		updateMusic(false);
	}
	
	private void addMoveTracks() 
	{
	move.addTrack("PriestYes1", new Bishop());
	move.addTrack("PriestYes2", new Bishop());
	
	move.addTrack("ArthasYes3", new King());
	move.addTrack("SpellbreakerYes5", new King());

	move.addTrack("HeroArchMageYes4", new Knight());
	move.addTrack("KnightWhat1", new Knight());

	move.addTrack("PeasantYes2", new Pawn());
	move.addTrack("PeasantYes3", new Pawn());
	move.addTrack("PeasantYes4", new Pawn());
	move.addTrack("FootmanYes2", new Pawn());

	move.addTrack("SorceressYes2", new Queen());
	move.addTrack("SorceressYes3", new Queen());
	move.addTrack("SuccubusPissed1", new Queen());
	move.addTrack("SuccubusYes3", new Queen());

	move.addTrack("BallistaYes1", new Rook());
	move.addTrack("BallistaReady1", new Rook());
	}

	private void addSelectedTracks() 
	{
		selected.addTrack("PriestWhat1", new Bishop());
		selected.addTrack("PriestWhat2", new Bishop());
		
		selected.addTrack("EvilArthasWhat2", new King());
		selected.addTrack("UtherPissed1", new King());
		
		selected.addTrack("KnightNoRiderWhat1", new Knight());
		selected.addTrack("KnightReady1", new Knight());
		selected.addTrack("KnightWhat1", new Knight());
		selected.addTrack("KnightPissed4", new Knight());
		
		selected.addTrack("PeasantWhat1" , new Pawn());
		selected.addTrack("VillagerMAWhat1", new Pawn());
		selected.addTrack("VillagerF1", new Pawn());
		selected.addTrack("PeasantWhat4", new Pawn());
		selected.addTrack("GruntPissed2", new Pawn());
		selected.addTrack("FootmanWhat1", new Pawn());
		selected.addTrack("FootmanReady1", new Pawn());
		
		selected.addTrack("JainaWhat4", new Queen());
		selected.addTrack("ArcherPissed2", new Queen());
		selected.addTrack("SorceressWhat4", new Queen());
		selected.addTrack("SuccubusWhat1", new Queen());
		selected.addTrack("SuccubusPissed3", new Queen());
		
		selected.addTrack("BallistaWhat1", new Rook());
		selected.addTrack("BallistaWhat2", new Rook());
	}
	
	private void addDeathTracks() 
	{
		death.addTrack("PriestDeath", new Bishop());
		
		death.addTrack("KnightDeath", new Knight());
		death.addTrack("HeroArchMageDeath", new Knight());
		
		death.addTrack("VillagerMaleDeath1", new Pawn());
		death.addTrack("FootmanDeath321", new Pawn());
		death.addTrack("VillagerChildDeath1", new Pawn());
		
		death.addTrack("JainaOnFootDeath1", new Queen());
		death.addTrack("SuccubusDeath1", new Queen());
		
		death.addTrack("BallistaDeath1", new Rook());
		death.addTrack("BuildingDeathSmallHuman", new Rook());
		
		death.addTrack("HeroMountainKingWhat3", new King());
	}
	
	private void addAttackSoundEffects() 
	{
		attackSoundEffects.add("MetalHeavyChopFlesh1");
		attackSoundEffects.add("MetalHeavyChopFlesh2");
		attackSoundEffects.add("MetalHeavyChopFlesh3");
	}
	
	public boolean isMuted() {
		return muted;
	}

	public void setMuted(boolean muted) {
		this.muted = muted;
		stop();
	}

}
