/*
 * Copyright 2006-2008 The MZmine Development Team
 * 
 * This file is part of MZmine.
 * 
 * MZmine is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * MZmine is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * MZmine; if not, write to the Free Software Foundation, Inc., 51 Franklin St,
 * Fifth Floor, Boston, MA 02110-1301 USA
 */

package net.sf.mzmine.modules.dataanalysis.projectionplots;

import org.jfree.chart.labels.XYZToolTipGenerator;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;

public class ProjectionPlotToolTipGenerator implements XYZToolTipGenerator {

	private enum LabelMode {
		FileName, FileNameAndParameterValue
	};

	private LabelMode labelMode;

	ProjectionPlotToolTipGenerator(ProjectionPlotParameters parameters) {

		if (parameters.getParameterValue(ProjectionPlotParameters.coloringType) == ProjectionPlotParameters.ColoringTypeSingleColor)
			labelMode = LabelMode.FileName;

		if (parameters.getParameterValue(ProjectionPlotParameters.coloringType) == ProjectionPlotParameters.ColoringTypeByFile)
			labelMode = LabelMode.FileName;

		if (parameters.getParameterValue(ProjectionPlotParameters.coloringType) == ProjectionPlotParameters.ColoringTypeByParameterValue)
			labelMode = LabelMode.FileNameAndParameterValue;

	}

	private String generateToolTip(ProjectionPlotDataset dataset, int item) {

		
		switch (labelMode) {
		
		case FileName:
		default:
			return dataset.getRawDataFile(item).toString();

		case FileNameAndParameterValue:
			String ret = "<html>";
			ret += dataset.getRawDataFile(item).toString() + "<br>";
			ret += "Parameter value: ";
			
			int groupNumber = dataset.getGroupNumber(item);
			Object paramValue = dataset.getGroupParameterValue(groupNumber);
			if (paramValue != null)
				ret += paramValue.toString();
			
			ret += "</html>";
			
			return ret;
		}

	}

	public String generateToolTip(XYDataset dataset, int series, int item) {
		if (dataset instanceof ProjectionPlotDataset)
			return generateToolTip((ProjectionPlotDataset)dataset, item);
		else
			return null;
	}

	public String generateToolTip(XYZDataset dataset, int series, int item) {
		if (dataset instanceof ProjectionPlotDataset)
			return generateToolTip((ProjectionPlotDataset)dataset, item);
		else
			return null;
	}
}
