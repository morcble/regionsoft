/**
 * record the whole client status
 */
var SystemData= {
		getDropdownList:function(){
			return window.DropdownList;
		},
		getAppData:function(){
			return window.appData;
		}
	}
function HashMap(){  
    var length = 0;   
    var obj = new Object();  
    this.isEmpty = function(){  
        return length == 0;  
    };   
    this.containsKey=function(key){  
        return (key in obj);  
    };  
    this.containsValue=function(value){  
        for(var key in obj){  
            if(obj[key] == value){  
                return true;  
            }  
        }  
        return false;  
    };   
    this.put=function(key,value){  
        if(!this.containsKey(key)){  
            length++;  
        }  
        obj[key] = value;  
    };  
    this.get=function(key){  
        return this.containsKey(key)?obj[key]:null;  
    };  
  
    this.remove=function(key){  
        if(this.containsKey(key)&&(delete obj[key])){  
            length--;  
        }  
    };  
    this.values=function(){  
        var _values= new Array();  
        for(var key in obj){  
            _values.push(obj[key]);  
        }  
        return _values;  
    };   
    this.keySet=function(){  
        var _keys = new Array();  
        for(var key in obj){  
            _keys.push(key);  
        }  
        return _keys;  
    };  
    this.size = function(){  
        return length;  
    };   
    this.clear = function(){  
        length = 0;  
        obj = new Object();  
    };  
}  


function Application(){
	this.tokenId = null;
	this.lastAccessTime = null;
	this.requestCount = 0;
	this.dsCacheMap = new HashMap();
}

if(typeof parent.appData == "undefined"){
	this.appData= new Application();
	this.appData.tokenId="dummyId";
}
else{
	this.appData = parent.appData;
}



if(typeof parent.DropdownList == "undefined"){
	this.DropdownList = new HashMap();
	this.DropdownList.put("PROVINCE",[{"SC":"SICHUAN"},{"HB":"HE BEI"},{"HN":"HAI NAN"},{"JS":"JIANG SU"}]);
	this.DropdownList.put("CITY",[{"SC":"CHENGDU"},{"BJ":"BEI JING"},{"SZ":"SHEN ZHEN"},{"SH":"SHANG HAI"}]);
	
}
else{
	this.DropdownList=parent.DropdownList;

}