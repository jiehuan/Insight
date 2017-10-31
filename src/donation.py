# -*- coding: utf-8 -*-

import os
import codecs
import numpy as np
import pandas as pd
import sys

def politic(filename, outName):
   
    data = pd.read_table(filename, sep = "|", header = None, parse_dates = [13], dtype  = {0:str, 10:str, 14:np.float}, error_bad_lines = False)
    data_sel = data[data[15].isnull()]
    data_sel2 = data_sel[[0, 13, 14]]
    data_sel2['date'] = pd.to_datetime(data_sel2[13], format='%m%d%Y', errors='coerce')
    data_sel3 = data_sel2.loc[(-data_sel2['date'].isnull()) & (data_sel2[14]>0), [0, 13, 14, 'date']]
    
    dg = data_sel3.groupby([0, 13, 'date'], as_index = False)[14].agg({'median':np.median}).round().astype(str)
    
    dg['count'] = data_sel3.groupby([0, 13, 'date'])[14].count().reset_index(drop = True).round().astype(str)
    dg['sum'] = data_sel3.groupby([0, 13, 'date'])[14].sum().reset_index(drop = True).round().astype(str)
    dg = dg.sort_values(by = [0, 'date']).drop(['date'], axis = 1).reset_index(drop = True)
    result = codecs.open(outName, 'w', 'utf-8')
    for i in range(dg.shape[0]):
        res = dg.loc[i]
        res['median'] = str(res['median']).replace(".0", "")
        res['sum'] = str(res['sum']).replace(".0", "")
        res['count'] = str(res['count']).replace(".0", "")
        line = "|".join(list(res))
        result.write(line)
        if i < (dg.shape[0]-1):
            result.write('\r\n')
    result.close()
        
    
    
if __name__ == '__main__':
    filenames = sys.argv
    #filenames = ['itcont.txt', 'medianvals_by_date.txt']
    filein = filenames[1]
    fileout = filenames[2]
    
    politic(filein, fileout)    
    
    