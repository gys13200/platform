package com.guoys.platform.webcomponents.entity.DO;

import com.guoys.platform.persistence.annotation.NameStyle;
import com.guoys.platform.persistence.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;
import java.util.Objects;

@Table(name = "platform_role")
@NameStyle
public class Role {

	@Id
	private String id;

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", functions=" + functions + "]";
	}

	private String name;

	private String description;

	private String homePage;

	private String homePageTitle;

	@Transient
	private String functionIds;

	@Transient
	private List<Function> functions;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public List<Function> getFunctions() {
		return functions;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

    public String getHomePageTitle() {
        return homePageTitle;
    }

    public void setHomePageTitle(String homePageTitle) {
        this.homePageTitle = homePageTitle;
    }

    public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}

    public String getFunctionIds() {
	    if(this.functions == null || this.functions.isEmpty()){
	        return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Function function : this.functions) {
            sb.append(function.getId()).append(",");
        }
        this.functionIds = sb.deleteCharAt(sb.length()-1).toString();
        return functionIds;
    }
}