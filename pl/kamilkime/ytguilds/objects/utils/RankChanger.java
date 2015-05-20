package pl.kamilkime.ytguilds.objects.utils;

import pl.kamilkime.ytguilds.objects.Rank;

public class RankChanger {

	public static double[] getExpectations(Rank a, Rank b){
		double[] expecs = new double[2];
		expecs[0] = 1 / (1 + Math.pow(10, (b.getRank() - a.getRank()) / 400));
		expecs[1] = 1 / (1 + Math.pow(10, (a.getRank() - b.getRank()) / 400));
		return expecs;
	}
	
	public static int getConstant(int ranking){
		if(ranking < 2000) return 32;
		if(ranking < 2401) return 24;
		return 16;
	}
	
	public static int[] getNewRankings(Rank a, Rank b, boolean victoryA){
		double[] ests = getExpectations(a, b);
		int[] ret = new int[2];
		int newA = (int) (a.getRank() + getConstant(a.getRank()) * ((victoryA ? 1 : 0) - ests[0]));
		int newB = (int) (b.getRank() + getConstant(b.getRank()) * ((victoryA ? 0 : 1) - ests[1]));
		ret[0] = Math.round(newA);
		ret[1] = Math.round(newB);
		return ret;
	}
}