/*
	jtxtproc
	Copyright (C) 2014  Edward Sargsyan
	avr_libs is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.
	jtxtproc is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.
	You should have received a copy of the GNU General Public License
	along with avr_libs.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.ssc.txtproc;

import java.util.List;

public class Result {

    private String   mNormalizedByPorter = null;
    private List<String> mLancasterStems = null;
    private String   mNormalizedByLancaster = null;
    private List<String> mTokens = null;
    private List<String> mStops = null;
    private List<String> mNonStops = null;
    private List<Double> mLevenshtineDist = null;
    private List<Double> mJaroWinkler = null;
    private List<List<String>> mNGrams = null;
    private String   mOriginalText = null;

    public Result() {

    }

    private List<String> mPorterStems = null;

    public List<String> getPorterStems() {
        return mPorterStems;
    }

    public void setPorterStems(List<String> stems) {
        this.mPorterStems = stems;
    }

    public String getNormalizedByPorter() {
        return mNormalizedByPorter;
    }

    public void setNormalizedByPorter(String text) {
        this.mNormalizedByPorter = text;
    }

    public List<String> getLancasterStems() {
        return mLancasterStems;
    }

    public void setLancasterStems(List<String> stems) {
        this.mLancasterStems = stems;
    }

    public String getNormalizedByLancaster() {
        return mNormalizedByLancaster;
    }

    public void setNormalizedByLancaster(String text) {
        this.mNormalizedByLancaster = text;
    }

    public List<String> getTokens() {
        return mTokens;
    }

    public void setTokens(List<String> tokens) {
        this.mTokens = tokens;
    }

    public List<String> getStops() {
        return mStops;
    }

    public void setStops(List<String> stops) {
        this.mStops = stops;
    }

    public List<String> getNonStops() {
        return mNonStops;
    }

    public void setNonStops(List<String> nonstops) {
        this.mNonStops = nonstops;
    }

    public List<Double> getLevenshtineDist() {
        return mLevenshtineDist;
    }

    public void setLevenshtineDist(List<Double> dist) {
        this.mLevenshtineDist = dist;
    }

    public List<Double> getJaroWankler() {
        return mJaroWinkler;
    }

    public void setJaroWinkler(List<Double> dist) {
        this.mJaroWinkler = dist;
    }

    public List<List<String>> getNGrams() {
        return mNGrams;
    }

    public void setNGrams(List<List<String>> NGrams) {
        this.mNGrams = NGrams;
    }

    public String getOriginalText() {
        return mOriginalText;
    }

    public void setOriginalText(String mOriginalText) {
        this.mOriginalText = mOriginalText;
    }
}
