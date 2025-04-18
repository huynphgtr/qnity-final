// helper format
function formatCurrency(n) {
    return '₫' + Number(n).toLocaleString();
  }
  
  // Geocode via Nominatim OpenStreetMap
  const VIEWBOX_QN = {
    left: 109.10,
    top:  13.85,
    right:109.30,
    bottom:13.70
  };
  
  function geocode(address) {
    const { left, top, right, bottom } = VIEWBOX_QN;
    const viewbox = `${left},${top},${right},${bottom}`;
    const url = `https://nominatim.openstreetmap.org/search` +
      `?format=json` +
      `&q=${encodeURIComponent(address)}` +
      `&viewbox=${viewbox}` +
      `&bounded=1` +    
      `&limit=1`;      
  
    return fetch(url)
      .then(res => res.json())
      .then(results =>
        results.length
          ? { lat: +results[0].lat, lng: +results[0].lon }
          : null
      )
      .catch(() => null);
  }
  // Render panel chi tiết
  function renderItinerary(data) {
    const panel = document.getElementById('itinerary-panel');
    panel.innerHTML = '';
  
    Object.keys(data).forEach((dateStr, idx) => {
      const dayArr = data[dateStr];
      const section = document.createElement('div');
      section.className = 'day-section';
  
      section.innerHTML = `
        <h3 class="day-title">Day ${idx + 1}: ${dateStr}</h3>
        <ul class="schedule-list"></ul>
      `;
      const ul = section.querySelector('ul');
      dayArr.forEach(item => {
        const li = document.createElement('li');
        li.className = 'schedule-item';
        li.innerHTML = `
          <span class="time">${item.time}</span>
          <span class="detail">${item.location} – ${item['activity description']}</span>
          <span class="cost">${formatCurrency(item['estimated cost'])}</span>
        `;
        ul.appendChild(li);
      });
  
      const total = dayArr.reduce((s, x) => s + Number(x['estimated cost']), 0);
      const divTotal = document.createElement('div');
      divTotal.className = 'day-total';
      divTotal.textContent = `Total: ${formatCurrency(total)}`;
      section.appendChild(divTotal);
  
      panel.appendChild(section);
    });
  }
  
  // Khởi tạo Leaflet map, marker + polyline
  async function initMap() {
    // 1. Map center và tile layer
    const map = L.map('map').setView([13.78, 109.20], 11);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; OpenStreetMap contributors'
    }).addTo(map);
  
    // 2. Lấy data từ localStorage
    const raw = localStorage.getItem('AI response');
    if (!raw) return console.warn('No itinerary data');
    const data = JSON.parse(raw);
  
    // 3. Render panel
    renderItinerary(data);
  
    // 4. Geocode & đặt marker
    const coords = [];
    const items = Object.values(data).flat();
    for (const item of items) {
      const pos = await geocode(item.location);
      if (pos) {
        L.marker([pos.lat, pos.lng])
          .addTo(map)
          .bindPopup(`<strong>${item.time}</strong><br>${item.location}`);
        coords.push([pos.lat, pos.lng]);
      }
    }
  
    // 5. Vẽ polyline và fitBounds
    if (coords.length) {
      const line = L.polyline(coords, { color: getComputedStyle(document.documentElement).getPropertyValue('--primary-color').trim(), weight: 4 }).addTo(map);
      map.fitBounds(line.getBounds(), { padding: [40, 40] });
    }
  }
  
  // Chạy init khi DOM sẵn sàng
  document.addEventListener('DOMContentLoaded', initMap);
  