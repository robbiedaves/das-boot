package com.boot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.boot.controller.ShipwreckController;
import com.boot.model.Shipwreck;
import com.boot.repository.ShipwreckRepository;

public class ShipwreckControllerTest {
	
	@InjectMocks
	private ShipwreckController sc;
	
	@Mock
	private ShipwreckRepository shipwreckRepository;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testShipwreckGet() {
		Shipwreck sw = new Shipwreck();
		sw.setId(1l);
		when(shipwreckRepository.findOne(1l)).thenReturn(sw);
		
		Shipwreck wreck = sc.get(1l);
		
		verify(shipwreckRepository).findOne(1l);
		
		// JUnit Assertion
		//assertEquals(1l, wreck.getId().longValue());
		// Hamcrest Assertion
		assertThat(wreck.getId(), is(1l));
	}
	
	@Test
	public void testShipwreckCreate() {
		Shipwreck sw = new Shipwreck();
		sw.setId(1l);
		when(shipwreckRepository.saveAndFlush(sw)).thenReturn(sw);
		
		Shipwreck swNew = sc.create(sw);
		
		verify(shipwreckRepository).saveAndFlush(sw);
		
		assertEquals(swNew.getId().longValue(), sw.getId().longValue());
	}
	
	@Test
	public void testShipwreckUpdate() {
		Shipwreck sw = new Shipwreck();
		sw.setId(1l);
		sw.setName("shipwreck new name test");
		sw.setDescription("shipwreck new description");
		
		when(shipwreckRepository.findOne(1l)).thenReturn(sw);
		when(shipwreckRepository.saveAndFlush(sw)).thenReturn(sw);
		
		Shipwreck wreck = sc.update(1l, sw);
		
		verify(shipwreckRepository).findOne(1l);
		verify(shipwreckRepository).saveAndFlush(sw);
		
		assertEquals(wreck.getId().longValue(), sw.getId().longValue());
		assertEquals(wreck.getName(), sw.getName());
		assertEquals(wreck.getDescription(), sw.getDescription());
	}
	
	@Test
	public void testShipwreckDelete() {
		Shipwreck sw = new Shipwreck();
		sw.setId(1l);
		
		when(shipwreckRepository.findOne(1l)).thenReturn(sw); 
		
		Shipwreck wreck = sc.delete(1l);
		
		verify(shipwreckRepository).findOne(1l);
		verify(shipwreckRepository).delete(sw);
		
		assertEquals(1l, wreck.getId().longValue());
	}
	
	@Test
	public void testShipwreckList() {
		List<Shipwreck> swList = new ArrayList<Shipwreck>();
		
		Shipwreck sw1 = new Shipwreck();
		sw1.setId(1l);
		swList.add(sw1);
		Shipwreck sw2 = new Shipwreck();
		sw2.setId(2l);
		swList.add(sw2);
		
		when(shipwreckRepository.findAll()).thenReturn(swList);
		
		List<Shipwreck> swReturnList = sc.list();
		
		verify(shipwreckRepository).findAll();
		
		assertTrue(swReturnList.contains(swList.get(0)));
		assertTrue(swReturnList.contains(swList.get(1)));
	}
	
}
