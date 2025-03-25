package org.nwea.oauthproxy.service.filter;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.web.filter.GenericFilterBean;
/*
 * FilterService - utility class for doing filtered searches against collections
 * 
 *  User the @ModelAttribute to determine attributes to filter against collection
 *
 *	@ModelAttribute ("agencies") Agencies agencies,
 *	@ModelAttribute ("phones") Phones phones,
 *	@ModelAttribute ("addresses") Addresses addresses,
 *	@ModelAttribute ("products")  Products products, BindingResult result, Model model,	
 *	@ModelAttribute ("modules") Modules modules,
 */

//@Component
public class FilterService extends GenericFilterBean{
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	HashMap setMongoSearchCriteria(String subDoc, HashMap hm, Object o) {
		try {
			Class<?> className = o.getClass();
			System.out.println("Class name: " + className.getName());
			BeanInfo beanInfo = Introspector.getBeanInfo(className);
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			beanInfo.getPropertyDescriptors();
			Object propertyValue;
			for (PropertyDescriptor pd : pds)
			{
				System.out.println("Field name: " +pd.getName());
				System.out.println("Field type: " +pd.getPropertyType());
				if (!"class".equals(pd.getName()))
				{		
					//final Object propertyValue = pd.getReadMethod().invoke(o);
					if(pd.getPropertyType().toString().contains("String")) {
						if (!pd.getName().equalsIgnoreCase("id")) {
							if (pd.getReadMethod().invoke(o) != null) {
								propertyValue = pd.getReadMethod().invoke(o);
								if (!subDoc.equalsIgnoreCase("agencies")){
									if (subDoc.equalsIgnoreCase("products") && (!pd.getName().equalsIgnoreCase("agencyCode"))) {
										if(!pd.getName().equalsIgnoreCase("lastModifiedDate")) {
											hm.put(subDoc+"."+pd.getName(), propertyValue);
										}
									} else if (subDoc.equalsIgnoreCase("phones")) {
										hm.put(subDoc+"."+pd.getName(), propertyValue);
									} else if (subDoc.equalsIgnoreCase("addresses")) {
										hm.put(subDoc+"."+pd.getName(), propertyValue);
									}else if (subDoc.equalsIgnoreCase("products.modules")) {
										hm.put(subDoc+"."+pd.getName(), propertyValue);
									}
								} else {
									hm.put(pd.getName(), propertyValue);
								}
								System.out.println(subDoc+"."+pd.getName() + ": "+ propertyValue);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hm;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
//		System.out.println(user.getName());
		if (httpRequest.authenticate(httpResponse)) 
//			profileUserDetailService.loadUserByUsername(user.getName());
			System.out.println(httpResponse.getStatus());
		
	}
}
